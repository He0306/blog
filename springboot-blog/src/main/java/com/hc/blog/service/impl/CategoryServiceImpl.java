package com.hc.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hc.blog.common.constants.Constants;
import com.hc.blog.common.lang.R;
import com.hc.blog.entity.Article;
import com.hc.blog.entity.Category;
import com.hc.blog.entity.vo.CategoryPageVo;
import com.hc.blog.entity.vo.CategoryVo;
import com.hc.blog.mapper.ArticleMapper;
import com.hc.blog.mapper.CategoryMapper;
import com.hc.blog.service.ICategoryService;
import com.hc.blog.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 何超
 * @since 2022-10-23
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    ArticleMapper articleMapper;

    @Cacheable(value = "pageListCategory", key = "#pageNum+" + "'_'+#pageSize+" + "'_'+#categoryName")
    @Override
    public R pageList(Integer pageNum, Integer pageSize, String categoryName) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(categoryName)) {
            queryWrapper.like(Category::getCategoryName, categoryName);
        }
        Page<Category> page = new Page<>(pageNum, pageSize);
        Page<Category> categoryPage = categoryMapper.selectPage(page, queryWrapper);
        List<CategoryPageVo> categoryPageVoList = new ArrayList<>();
        for (Category category : categoryPage.getRecords()) {
            LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Article::getCategoryId, category.getId());
            Long count = articleMapper.selectCount(wrapper);
            CategoryPageVo categoryPageVo = new CategoryPageVo();
            categoryPageVo.setId(category.getId());
            categoryPageVo.setCategoryName(category.getCategoryName());
            categoryPageVo.setArticleCount(count);
            categoryPageVo.setCreateTime(category.getCreateTime());
            categoryPageVoList.add(categoryPageVo);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("categoryList", categoryPageVoList);
        map.put("total", categoryPage.getTotal());
        return R.okResult(map);
    }

    /**
     * 前台查询状态正常的标签
     *
     * @return
     */
    @Cacheable(value = "list.category", key = "#root.methodName")
    @Override
    public R getCategoryList() {
        //查询文章表 状态为已发布的文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, Constants.STATUS_NORMAL);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        //获取文章分类id 并且去重
        Set<String> categoryIds = articles.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());
        //查询分类表
        List<Category> categories = categoryMapper.selectBatchIds(categoryIds);
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return R.okResult(categoryVos);
    }
}
