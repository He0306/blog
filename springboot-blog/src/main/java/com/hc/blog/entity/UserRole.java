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
 * @since 2022-10-02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user_role")
@ApiModel(value = "UserRole对象", description = "")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("角色ID")
    private String roleId;

    @ApiModelProperty("用户ID")
    private String userId;


    @ApiModelProperty("插入时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;


}
