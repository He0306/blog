package com.hc.blog.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: 何超
 * @date: 2022/11/27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotArticleVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("文章ID")
    private String id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("访问量")
    private Integer viewCount;
}
