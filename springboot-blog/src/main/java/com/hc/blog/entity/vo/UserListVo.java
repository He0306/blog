package com.hc.blog.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: 何超
 * @date: 2022/10/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID")
    private String id;

    @ApiModelProperty("登录账号")
    private String username;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("角色编码")
    private String roleName;

    @ApiModelProperty("邮箱")
    private String email;
}
