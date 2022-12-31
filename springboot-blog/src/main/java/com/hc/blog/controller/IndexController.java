package com.hc.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hc.blog.common.lang.R;
import com.hc.blog.entity.Article;
import com.hc.blog.entity.Category;
import com.hc.blog.entity.vo.CategoryPageVo;
import com.hc.blog.entity.vo.IndexVo;
import com.hc.blog.service.IArticleService;
import com.hc.blog.service.ICategoryService;
import com.hc.blog.service.ITagService;
import com.hc.blog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 何超
 * @date: 2022/11/29
 */
@RestController
@RequestMapping("/admin")
public class IndexController {

    @Autowired
    IArticleService articleService;

    @Autowired
    ITagService tagService;

    @Autowired
    ICategoryService categoryService;

    @Autowired
    IUserService userService;

    @GetMapping("/index")
    public R index() {

        IndexVo indexVo = new IndexVo();
        //用户数量
        indexVo.setUserCount(userService.count());
        //文章量
        indexVo.setArticleCount(articleService.count());
        //浏览量
        indexVo.setViewsCount(articleService.viewCount());
        //查询所有标签
        indexVo.setTagList(tagService.list());
        //查询分类的名称和文章量
        List<Category> list = categoryService.list();
        List<CategoryPageVo> categoryPageVoList = new ArrayList<>();
        for (Category category : list) {
            CategoryPageVo categoryPageVo = new CategoryPageVo();
            categoryPageVo.setArticleCount(articleService.count(new LambdaQueryWrapper<Article>().eq(Article::getCategoryId, category.getId())));
            categoryPageVo.setCategoryName(category.getCategoryName());
            categoryPageVo.setId(category.getId());
            categoryPageVoList.add(categoryPageVo);
        }
        //查询文章的标题和浏览量
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper.select(Article::getTitle, Article::getViewCount);
        articleLambdaQueryWrapper.orderByDesc(Article::getViewCount);
        articleLambdaQueryWrapper.eq(Article::getStatus, true);
        articleLambdaQueryWrapper.last("limit 8");
        indexVo.setArticleList(articleService.list(articleLambdaQueryWrapper));
        indexVo.setCategoryList(categoryPageVoList);
        return R.okResult(indexVo);
    }
}
