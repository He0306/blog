package com.hc.blog.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 何超
 * @date: 2022/10/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentUserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("权限集合")
    private List<String> permissions;

    @ApiModelProperty("角色集合")
    private List<String> roles;

    @ApiModelProperty("用户信息")
    private UserInfoVo userInfoVo;
}
