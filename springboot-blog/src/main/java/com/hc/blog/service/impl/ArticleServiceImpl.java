package com.hc.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hc.blog.common.constants.Constants;
import com.hc.blog.common.constants.MqConstants;
import com.hc.blog.common.lang.R;
import com.hc.blog.entity.Article;
import com.hc.blog.entity.ArticleTag;
import com.hc.blog.entity.Category;
import com.hc.blog.entity.dto.AddArticleDto;
import com.hc.blog.entity.vo.ArticleDetailVo;
import com.hc.blog.entity.vo.ArticleListVo;
import com.hc.blog.entity.vo.HotArticleVo;
import com.hc.blog.entity.vo.PageVo;
import com.hc.blog.mapper.*;
import com.hc.blog.service.IArticleService;
import com.hc.blog.thread.ThreadService;
import com.hc.blog.utils.BeanCopyUtils;
import com.hc.blog.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 何超
 * @since 2022-10-23
 */
@Slf4j
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    ArticleTagMapper articleTagMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    TagMapper tagMapper;

    @Autowired
    ThreadService threadService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    RestHighLevelClient client;

    @Cacheable(value = "pageListArticle", key = "#pageNum+" + "'_'+#pageSize+" + "'_'+#title+" + "'_'+#summary")
    @Override
    public R pageList(Integer pageNum, Integer pageSize, String title, String summary) {
        Page<Article> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(title)) {
            queryWrapper.like(Article::getTitle, title);
        }
        if (StringUtils.hasText(summary)) {
            queryWrapper.like(Article::getSummary, summary);
        }
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        for (Article article : articlePage.getRecords()) {
            article.setNickName(userMapper.selectById(article.getUserId()).getNickName());
        }
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articlePage.getRecords(), ArticleListVo.class);
        Map<String, Object> map = new HashMap<>();
        map.put("total", articlePage.getTotal());
        map.put("records", articleListVos);
        return R.okResult(map);
    }

    @Cacheable(value = "article", key = "#id")
    @Override
    public R getByArticleId(String id) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, Constants.STATUS_NORMAL);

        Article article = articleMapper.selectById(id);

        LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleTag::getArticleId, id);
        List<ArticleTag> articleTags = articleTagMapper.selectList(wrapper);
        List<String> collect = articleTags.stream()
                .map(ArticleTag::getTagId)
                .collect(Collectors.toList());
        article.setTags(collect);
        return R.okResult(article);
    }

    @CacheEvict(value = "pageListArticle", allEntries = true)
    @Override
    public R add(AddArticleDto addArticleDto) {
        Article article = BeanCopyUtils.copyBean(addArticleDto, Article.class);
        if (addArticleDto.getId() == null) {
            //新增博客
            article.setStatus(true);
            article.setUserId(SecurityUtils.getUserId());
            articleMapper.insert(article);
            List<ArticleTag> articleTags = addArticleDto.getTags().stream()
                    .map(tagId -> new ArticleTag(article.getId(), tagId))
                    .collect(Collectors.toList());
            //添加 博客和标签的关联
            for (ArticleTag articleTag : articleTags) {
                articleTagMapper.insert(articleTag);
            }
            rabbitTemplate.convertAndSend(MqConstants.BLOG_INSERT_QUEUE, MqConstants.BLOG_INSERT_KEY, article.getId());
        } else {
            //修改博客
            articleMapper.updateById(article);
            //删除标签
            List<String> tags = addArticleDto.getTags();
            for (String tag : tags) {
                LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(ArticleTag::getTagId, tag);
                queryWrapper.eq(ArticleTag::getArticleId, addArticleDto.getId());
                articleTagMapper.delete(queryWrapper);
            }

            List<ArticleTag> articleTags = addArticleDto.getTags().stream()
                    .map(tagId -> new ArticleTag(article.getId(), tagId))
                    .collect(Collectors.toList());
            //添加 博客和标签的关联
            for (ArticleTag articleTag : articleTags) {
                articleTagMapper.insert(articleTag);
            }
            rabbitTemplate.convertAndSend(MqConstants.BLOG_INSERT_QUEUE, MqConstants.BLOG_INSERT_KEY, article.getId());
        }
        return R.okResult();
    }

    @CacheEvict(value = "pageListArticle", allEntries = true)
    @Override
    public R removeArticle(String id) {
        articleMapper.deleteById(id);
        articleTagMapper.deleteByArticleId(id);
        //发送消息
        rabbitTemplate.convertAndSend(MqConstants.BLOG_DELETE_QUEUE, MqConstants.BLOG_DELETE_KEY, id);
        return R.okResult();
    }

    @CacheEvict(value = "pageListArticle", allEntries = true)
    @Override
    public R batchRemoveArticle(List<String> ids) {
        articleMapper.deleteBatchIds(ids);
        for (String s : ids) {
            articleTagMapper.deleteByArticleId(s);
        }
        rabbitTemplate.convertAndSend(MqConstants.BLOG_DELETE_QUEUE, MqConstants.BLOG_DELETE_KEY, ids);
        return R.okResult();
    }

    /**
     * 分页查询前台文章
     *
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    @Cacheable(value = "list.article", key = "#pageNum+" + "'_'+#pageSize+" + "'_'+#categoryId")
    @Override
    public R articleList(Integer pageNum, Integer pageSize, String categoryId) {
        //添加查询文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(categoryId)) {
            queryWrapper.eq(Article::getCategoryId, categoryId);
        }
        queryWrapper.eq(Article::getStatus, Constants.STATUS_NORMAL);
        queryWrapper.orderByDesc(Article::getIsTop);
        //分页
        Page<Article> page = new Page<>(pageNum, pageSize);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        for (Article article : articlePage.getRecords()) {
            //查询分类名称
            article.setNickName(userMapper.selectById(article.getUserId()).getNickName());
            article.setCategoryName(categoryMapper.selectById(article.getCategoryId()).getCategoryName());
        }
        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articlePage.getRecords(), ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos, articlePage.getTotal());
        return R.okResult(pageVo);
    }

    /**
     * 最热文章
     *
     * @return
     */
    @Cacheable(value = "hotArticleList", key = "#root.methodName")
    @Override
    public R hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, Constants.STATUS_NORMAL);
        queryWrapper.orderByDesc(Article::getViewCount);
        Page<Article> page = new Page<>(1, 5);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        for (Article article : articlePage.getRecords()) {
            article.setViewCount(article.getViewCount());
            queryWrapper.orderByDesc(Article::getViewCount);
        }
        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(articlePage.getRecords(), HotArticleVo.class);
        return R.okResult(hotArticleVos);
    }

    /**
     * 文章详情
     *
     * @param id
     * @return
     */
    @Cacheable(value = "articleDetail", key = "#id")
    @Override
    public R getArticleDetail(String id) {
        //根据ID查询文章
        Article article = articleMapper.selectById(id);
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        articleDetailVo.setNickName(userMapper.selectById(article.getUserId()).getNickName());
        //查询文章绑定的tagIds
        List<String> tagIds = articleTagMapper.selectTagId(id);
        //根据tagIds查询标签名
        articleDetailVo.setTagName(tagMapper.selectTagName(tagIds));
        //根据分类ID查询分类名
        Category category = categoryMapper.selectById(articleDetailVo.getCategoryId());
        if (category != null) {
            articleDetailVo.setCategoryName(category.getCategoryName());
            articleDetailVo.setViewCount(article.getViewCount());
        }
        return R.okResult(articleDetailVo);
    }

    /**
     * 更新浏览次数
     *
     * @param id
     * @return
     */
    @CacheEvict(value = {"list.article", "articleDetail", "hotArticleList"}, allEntries = true)
    @Override
    public R updateViewCount(String id) {
        threadService.updateArticleViewCount(id);
        rabbitTemplate.convertAndSend(MqConstants.BLOG_INSERT_QUEUE, MqConstants.BLOG_INSERT_KEY, id);
        return R.okResult();
    }

    /**
     * 修改状态
     *
     * @param article
     * @return
     */
    @CacheEvict(value = {"pageListArticle,list.article"}, allEntries = true)
    @Override
    public R updateStatus(Article article) {
        articleMapper.updateById(article);
        rabbitTemplate.convertAndSend(MqConstants.BLOG_INSERT_QUEUE, MqConstants.BLOG_INSERT_KEY, article.getId());
        return R.okResult();
    }

    /**
     * 统计所有浏览量
     *
     * @return
     */
    @Override
    public Long viewCount() {
        return articleMapper.viewCount();
    }

    /**
     * 保存到草稿箱
     *
     * @param addArticleDto
     * @return
     */
    @CacheEvict(value = "pageListArticle", allEntries = true)
    @Override
    public R drafts(AddArticleDto addArticleDto) {
        Article article = BeanCopyUtils.copyBean(addArticleDto, Article.class);
        //新增博客
        article.setStatus(false);
        article.setUserId(SecurityUtils.getUserId());
        articleMapper.insert(article);
        List<ArticleTag> articleTags = addArticleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());
        //添加 博客和标签的关联
        for (ArticleTag articleTag : articleTags) {
            articleTagMapper.insert(articleTag);
        }
        return R.okResult();
    }

    /**
     * es搜索
     *
     * @param pageNum
     * @param pageSize
     * @param searchText
     * @return
     */
    @Override
    public R search(Integer pageNum, Integer pageSize, String searchText) {
        SearchRequest searchRequest = new SearchRequest("blog");
        if (!StringUtils.hasText(searchText)) {
            searchRequest.source().query(QueryBuilders.matchAllQuery());
        } else {
            searchRequest.source().query(QueryBuilders.matchQuery("all", searchText));
        }
        searchRequest.source().from((pageNum - 1) * pageSize);
        searchRequest.source().size(pageSize);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("查询错误！");
        }

        SearchHits hits = searchResponse.getHits();
        //总条数
        long total = hits.getTotalHits().value;
        SearchHit[] hitsHits = hits.getHits();
        List<ArticleListVo> articleListVoList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (SearchHit hitsHit : hitsHits) {
            String sourceAsString = hitsHit.getSourceAsString();
            try {
                ArticleListVo articleListVo = objectMapper.readValue(sourceAsString, ArticleListVo.class);
                articleListVoList.add(articleListVo);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        PageVo pageVo = new PageVo(articleListVoList,total);
        return R.okResult(pageVo);
    }
}
