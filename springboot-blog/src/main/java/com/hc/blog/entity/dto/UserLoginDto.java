package com.hc.blog.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author: 何超
 * @date: 2022/11/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {

    @ApiModelProperty("登录账号")
    @NotBlank(message = "登录账号不能为空")
    public String username;

    @ApiModelProperty("登录密码")
    @NotBlank(message = "登录密码不能为空")
    public String password;
}
