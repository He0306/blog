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
public class TagPageVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("标签ID")
    private String id;

    @ApiModelProperty("标签名称")
    private String tagName;

    @ApiModelProperty("文章数量")
    private Long articleCount;

    @ApiModelProperty("创建时间")
    private Date createTime;
}
