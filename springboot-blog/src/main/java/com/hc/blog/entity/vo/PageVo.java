package com.hc.blog.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 何超
 * @date: 2022/11/27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("数据")
    private List rows;

    @ApiModelProperty("数据条数")
    private Long total;
}
