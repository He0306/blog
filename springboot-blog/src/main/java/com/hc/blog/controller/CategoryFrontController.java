package com.hc.blog.controller;

import com.hc.blog.common.lang.R;
import com.hc.blog.service.ICategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 何超
 * @date: 2022/11/27
 */
@Api(tags = "前台分类模块")
@RestController
@RequestMapping("/category")
public class CategoryFrontController {

    @Autowired
    ICategoryService categoryService;

    @ApiOperation(value = "查询状态正常的标签")
    @GetMapping("/getCategoryList")
    public R getCategoryList() {
        return categoryService.getCategoryList();
    }
}
