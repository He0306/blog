package com.hc.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hc.blog.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 何超
 * @since 2022-10-23
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    Long viewCount();

    void updateViewCount(@Param("id") String id);
}
