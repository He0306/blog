package com.hc.blog.sensitive;

import org.json.JSONObject;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author: 何超
 * @date: 2022/12/22
 */
public class BaiDuAiCheck {

    public static JSONObject checkImg(MultipartFile img) throws IOException {
        byte[] files = FileCopyUtils.copyToByteArray(img.getInputStream());
        JSONObject jsonObject = BaiDuConfig.client.imageCensorUserDefined(files,null);
        return jsonObject;
    }

    public static JSONObject checkText(String text){
        JSONObject jsonObject = BaiDuConfig.client.textCensorUserDefined(text);
        return jsonObject;
    }
}
