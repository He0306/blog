package com.hc.blog.controller;

import com.hc.blog.common.lang.R;
import com.hc.blog.service.ILinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 何超
 * @date: 2022/11/28
 */
@Api(tags = "前台友链模块")
@RestController
@RequestMapping("/link")
public class LinkFrontController {

    @Autowired
    ILinkService linkService;

    @ApiModelProperty(value = "前台查询全部友链")
    @GetMapping("/getAllLink")
    public R getAllLink() {
        return linkService.getAllLink();
    }

}
