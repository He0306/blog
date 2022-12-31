package com.hc.blog.handler.security;

import com.alibaba.fastjson.JSON;
import com.hc.blog.common.lang.HttpCodeEnum;
import com.hc.blog.common.lang.R;
import com.hc.blog.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: 何超
 * @date: 2022/10/13
 */
//认证失败处理器
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        e.printStackTrace();

        R result = null;
        if (e instanceof BadCredentialsException) {
            result = R.errorResult(HttpCodeEnum.USERNAME_PASSWORD_NO_EXIST.getCode(), e.getMessage());
        } else if (e instanceof InsufficientAuthenticationException) {
            result = R.errorResult(HttpCodeEnum.NEED_LOGIN);
        } else {
            result = R.errorResult(HttpCodeEnum.SYSTEM_ERROR.getCode(), "认证或授权失败");
        }
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
    }
}
