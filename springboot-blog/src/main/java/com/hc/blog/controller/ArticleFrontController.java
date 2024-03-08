package com.hc.blog.controller;

import com.hc.blog.common.lang.R;
import com.hc.blog.service.IArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: 何超
 * @date: 2022/11/27
 */
@RestController
@RequestMapping("/article")
@Api(tags = "前台标签模块")
public class ArticleFrontController {

    @Autowired
    IArticleService articleService;

    /**
     * 分页查询全部文章
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    @ApiOperation(value = "分页查询全部文章")
    @GetMapping("/articleList")
    public R articleList(Integer pageNum, Integer pageSize, String categoryId) {
        return articleService.articleList(pageNum, pageSize, categoryId);
    }

    /**
     * 最热文章
     * @return
     */
    @ApiOperation(value = "最热文章")
    @GetMapping("/hotArticleList")
    public R hotArticleList() {
        return articleService.hotArticleList();
    }

    /**
     * 根据ID查询文章详情
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID查询文章详情")
    @GetMapping("/{id}")
    public R getArticleDetail(@PathVariable("id") String id) {
        return articleService.getArticleDetail(id);
    }

    /**
     * 更新浏览次数
     * @param id
     * @return
     */
    @ApiOperation(value = "更新浏览次数")
    @PutMapping("/updateViewCount/{id}")
    public R updateViewCount(@PathVariable("id") String id) {
        return articleService.updateViewCount(id);
    }

    /**
     * 搜索文章
     * @param pageNum
     * @param pageSize
     * @param all
     * @return
     */
    @ApiOperation(value = "搜索文章")
    @GetMapping("/search")
    public R searchArticle(Integer pageNum,Integer pageSize,String all){
        return articleService.search(pageNum,pageSize,all);
    }

}
