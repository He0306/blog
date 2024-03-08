package com.hc.blog.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 何超
 * @date: 2022/10/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryPageVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("分类ID")
    private String id;

    @ApiModelProperty("分类名称")
    private String categoryName;

    @ApiModelProperty("文章数量")
    private Long articleCount;

    @ApiModelProperty("创建时间")
    private Date createTime;
}
