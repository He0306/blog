package com.hc.blog.entity.vo;

import com.hc.blog.entity.Article;
import com.hc.blog.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 何超
 * @date: 2022/11/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Tag> tagList;

    private List<CategoryPageVo> categoryList;

    private List<Article> articleList;

    private Long userCount;

    private Long viewsCount;

    private Long articleCount;
}
