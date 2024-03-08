package com.hc.blog.entity.doc;

import com.hc.blog.entity.vo.ArticleListVo;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 何超
 * @date: 2022/12/29
 */
@Data
@NoArgsConstructor
public class ArticleDoc extends ArticleListVo {

    private String all;

    public ArticleDoc(ArticleListVo articleListVo) {
        super(articleListVo.getId(), articleListVo.getTitle(),
                articleListVo.getContent(), articleListVo.getSummary(),
                articleListVo.getCategoryId(), articleListVo.getCategoryName(),
                articleListVo.getCover(), articleListVo.getViewCount(),
                articleListVo.getNickName(), articleListVo.getCreateTime());
        this.all = articleListVo.getTitle() + articleListVo.getContent() + articleListVo.getSummary();
    }
}
