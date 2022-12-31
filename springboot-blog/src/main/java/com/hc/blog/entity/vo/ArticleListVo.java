package com.hc.blog.entity.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 何超
 * @date: 2022/11/27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleListVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("文章ID")
    private String id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("发布人昵称")
    private String nickName;

    @ApiModelProperty("文章摘要")
    private String summary;

    @ApiModelProperty("状态(1已发布，0未发布)")
    private Boolean status;

    @ApiModelProperty("分类ID")
    private String categoryId;

    @ApiModelProperty("所属分类名称")
    private String categoryName;

    @ApiModelProperty("文章内容")
    private String content;

    @ApiModelProperty("缩略图")
    private String cover;

    @ApiModelProperty("访问量")
    private Integer viewCount;

    @ApiModelProperty("创建时间")
    private Date createTime;

    public ArticleListVo(String id, String title, String content, String summary, String categoryId, String categoryName, String cover, Integer viewCount, String nickName, Date createTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.summary = summary;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.cover = cover;
        this.viewCount = viewCount;
        this.nickName = nickName;
        this.createTime = createTime;
    }
}
