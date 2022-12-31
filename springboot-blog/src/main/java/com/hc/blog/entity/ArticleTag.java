package com.hc.blog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author 何超
 * @since 2022-10-26
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("h_article_tag")
@ApiModel(value = "ArticleTag对象", description = "")
public class ArticleTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("文章ID")
    private String articleId;

    @ApiModelProperty("标签ID")
    private String tagId;

    private Date createDate;

    private Date updateDate;

    public ArticleTag(String articleId, String tagId) {
        this.articleId = articleId;
        this.tagId = tagId;
    }
}
