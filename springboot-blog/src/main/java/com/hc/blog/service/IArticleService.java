package com.hc.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hc.blog.common.lang.R;
import com.hc.blog.entity.Article;
import com.hc.blog.entity.dto.AddArticleDto;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 何超
 * @since 2022-10-23
 */
public interface IArticleService extends IService<Article> {

    R pageList(Integer pageNum, Integer pageSize, String title, String summary);

    R getByArticleId(String id);

    R add(AddArticleDto addArticleDto);

    R removeArticle(String id);

    R batchRemoveArticle(List<String> ids);

    /**
     * 分页查询前台文章
     *
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    R articleList(Integer pageNum, Integer pageSize, String categoryId);

    /**
     * 最热文章
     *
     * @return
     */
    R hotArticleList();

    /**
     * 文章详情
     *
     * @param id
     * @return
     */
    R getArticleDetail(String id);

    /**
     * 更新浏览次数
     *
     * @param id
     * @return
     */
    R updateViewCount(String id);

    /**
     * 修改状态
     *
     * @param article
     * @return
     */
    R updateStatus(Article article);

    /**
     * 统计所有浏览量
     *
     * @return
     */
    Long viewCount();

    /**
     * 保存到草稿箱
     *
     * @param addArticleDto
     * @return
     */
    R drafts(AddArticleDto addArticleDto);

    /**
     * es搜索
     * @param pageNum
     * @param pageSize
     * @param searchText
     * @return
     */
    R search(Integer pageNum, Integer pageSize, String searchText);

}
