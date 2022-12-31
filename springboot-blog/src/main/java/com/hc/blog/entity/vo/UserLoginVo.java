package com.hc.blog.entity.vo;

import com.hc.blog.entity.Menu;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 何超
 * @date: 2022/10/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("用户信息")
    private UserInfoVo userInfoVo;

    @ApiModelProperty("菜单集合")
    private List<Menu> menus;
}
