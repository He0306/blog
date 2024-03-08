package com.hc.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hc.blog.entity.ArticleTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 何超
 * @since 2022-10-26
 */
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

    void deleteByArticleId(@Param("article_id") String id);

    List<String> selectTagId(@Param("article_id") String id);
}
