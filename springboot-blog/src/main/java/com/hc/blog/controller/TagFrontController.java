package com.hc.blog.controller;

import com.hc.blog.common.lang.R;
import com.hc.blog.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 何超
 * @date: 2022/12/3
 */
@RestController
@RequestMapping("/tag")
public class TagFrontController {

    @Autowired
    ITagService tagService;

    /**
     * 查询所有便签名称
     *
     * @return
     */
    @GetMapping("/list")
    public R list() {
        return tagService.selectTagName();
    }
}
