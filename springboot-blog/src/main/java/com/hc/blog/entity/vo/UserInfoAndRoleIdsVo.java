package com.hc.blog.entity.vo;

import com.hc.blog.entity.Role;
import com.hc.blog.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 何超
 * @date: 2022/10/16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoAndRoleIdsVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("角色ID")
    private String roleId;

    @ApiModelProperty("角色集合")
    private List<Role> roles;

    @ApiModelProperty("用户")
    private User user;
}
