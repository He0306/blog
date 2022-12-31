package com.hc.blog.controller;

import com.hc.blog.common.lang.R;
import com.hc.blog.entity.User;
import com.hc.blog.entity.dto.RegisterUserDto;
import com.hc.blog.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author: 何超
 * @date: 2022/10/13
 */
@Api(tags = "登录模块")
@RestController
public class LoginController {

    @Autowired
    IUserService userService;

    /**
     * 登录
     *
     * @param user
     * @return
     */
    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public R login(@Valid @RequestBody User user) {
        return userService.login(user);
    }

    /**
     * 退出登录
     *
     * @return
     */
    @ApiOperation(value = "退出登录")
    @PostMapping("/admin/logout")
    public R logout() {
        return userService.logout();
    }

    /**
     * 注册
     *
     * @param registerUserDto
     * @return
     */
    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public R registerUser(@Valid @RequestBody RegisterUserDto registerUserDto) {
        return userService.register(registerUserDto);
    }
}
