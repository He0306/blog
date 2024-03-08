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
public class CategoryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("分类ID")
    private String id;

    @ApiModelProperty("分类名称")
    private String categoryName;
}
