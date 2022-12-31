package com.hc.blog.controller;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.view.PoiBaseView;
import com.hc.blog.common.lang.R;
import com.hc.blog.entity.OperationLog;
import com.hc.blog.service.IOperationLogService;
import com.hc.blog.service.impl.UploadServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * @author: 何超
 * @date: 2022/10/15
 */
@Api(tags = "头像上传模块")
@RestController
public class UploadController {

    @Autowired
    UploadServiceImpl uploadService;

    @Autowired
    IOperationLogService operationLogService;


    @ApiOperation(value = "头像上传")
    @PostMapping("/upload")
    public R upload(MultipartFile img) {
        return uploadService.upload(img);
    }

    /**
     * 导出日志信息
     *
     * @param map
     * @param response
     * @param request
     */
    @ApiOperation(value = "导出日志信息")
    @GetMapping("/export")
    public void export(ModelMap map, HttpServletResponse response, HttpServletRequest request) {
        List<OperationLog> list = operationLogService.list();
        ExportParams exportParams = new ExportParams("日志信息", "日志信息", ExcelType.XSSF);
        //导出数据
        map.put(NormalExcelConstants.DATA_LIST, list);
        //导出实体
        map.put(NormalExcelConstants.CLASS, OperationLog.class);
        //导出参数
        map.put(NormalExcelConstants.PARAMS, exportParams);
        //设置文件名称
        map.put(NormalExcelConstants.FILE_NAME, "日志信息表");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + exportParams.getTitle() + ".xlsx");
        PoiBaseView.render(map, request, response, NormalExcelConstants.EASYPOI_EXCEL_VIEW);
    }


}
