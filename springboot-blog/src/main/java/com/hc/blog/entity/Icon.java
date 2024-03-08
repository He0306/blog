package com.hc.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
 * @since 2022-10-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_icon")
@ApiModel(value = "Icon对象", description = "")
public class Icon implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("内容")
    private String value;

    @ApiModelProperty("类型")
    private String type;

    @TableLogic
    private Integer isDelete;

    private Date createTime;

    private Date updateTime;


}
