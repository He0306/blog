package com.hc.blog.controller;

import com.hc.blog.annotation.OptLog;
import com.hc.blog.common.lang.R;
import com.hc.blog.entity.User;
import com.hc.blog.entity.dto.RegisterUserDto;
import com.hc.blog.entity.dto.UserRetrieveDto;
import com.hc.blog.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.hc.blog.common.constants.OptTypeConst.LOGOUT;
import static com.hc.blog.common.constants.OptTypeConst.REGISTER;

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
    @OptLog(optType = LOGOUT)
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
    @OptLog(optType = REGISTER)
    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public R registerUser(@Valid @RequestBody RegisterUserDto registerUserDto) {
        return userService.register(registerUserDto);
    }

    /**
     * 发送验证码
     * @param email
     * @return
     */
    @ApiOperation(value = "发送验证码")
    @GetMapping("/sendCode")
    public R sendCode(@RequestParam("email") String email){
        return userService.sendCode(email);
    }

    /**
     * 找回密码
     * @param dto
     * @return
     */
    @ApiOperation(value = "找回密码")
    @PostMapping("/retrievePassword")
    public R retrievePassword(@RequestBody UserRetrieveDto dto){
        return userService.retrievePassword(dto);
    }
}
