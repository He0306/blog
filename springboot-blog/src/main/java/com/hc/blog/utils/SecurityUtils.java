package com.hc.blog.utils;

import com.hc.blog.handler.security.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author: 何超
 * @date: 2022/10/9
 * 获得当前登录用户工具类
 */
public class SecurityUtils {

    //获取用户
    public static LoginUser getLoginUser() {
        return (LoginUser) getAuthentication().getPrincipal();
    }

    //获取Authentication
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String getUserId() {
        return getLoginUser().getUser().getId();
    }

}
