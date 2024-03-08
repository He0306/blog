package com.hc.blog.service.impl;

import cn.hutool.core.date.DateTime;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.hc.blog.common.exception.ServiceException;
import com.hc.blog.common.lang.HttpCodeEnum;
import com.hc.blog.common.lang.R;
import com.hc.blog.sensitive.BaiDuAiCheck;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author: 何超
 * @date: 2022/10/15
 */
@Slf4j
@Service
public class UploadServiceImpl {

    //读取配置文件内容
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.keyid}")
    private String keyId;

    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;

    public R upload(MultipartFile img) {

        try {
            JSONObject jsonObject = BaiDuAiCheck.checkImg(img);
            if (jsonObject.get("conclusion").equals("不合规")){
                throw new ServiceException(HttpCodeEnum.IMG_NON_COMPLIANCE);
            }
            OSS ossClient = new OSSClientBuilder().build(endpoint, keyId, keySecret);

            InputStream inputStream = img.getInputStream();
            String filename = img.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replace("-", "");
            filename = uuid + filename;

            String datePath = new DateTime().toString("yyyy/MM/dd");
            filename = datePath + "/" + filename;

            ossClient.putObject(bucketName, filename, inputStream);

            ossClient.shutdown();

            String url = "https://" + bucketName + "." + endpoint + "/" + filename;
            return R.okResult(url);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException(HttpCodeEnum.FILE_TYPE_ERROR);
        }
    }
}
