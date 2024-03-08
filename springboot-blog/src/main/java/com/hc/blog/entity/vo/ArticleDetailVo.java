package com.hc.blog.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author: 何超
 * @date: 2022/11/27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("文章ID")
    private String id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("文章摘要")
    private String summary;

    @ApiModelProperty("所属分类id")
    private String categoryId;

    @ApiModelProperty("发布人昵称")
    private String nickName;

    @ApiModelProperty("所属分类名称")
    private String categoryName;

    @ApiModelProperty("缩略图")
    private String cover;

    @ApiModelProperty("文章内容")
    private String content;

    @ApiModelProperty("访问量")
    private Integer viewCount;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("标签名")
    private List<String> tagName;
}
