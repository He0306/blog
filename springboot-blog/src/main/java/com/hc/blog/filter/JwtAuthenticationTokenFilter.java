package com.hc.blog.filter;

import com.alibaba.fastjson.JSON;
import com.hc.blog.common.constants.Constants;
import com.hc.blog.common.lang.HttpCodeEnum;
import com.hc.blog.common.lang.R;
import com.hc.blog.config.RedisCache;
import com.hc.blog.handler.security.LoginUser;
import com.hc.blog.utils.JwtUtils;
import com.hc.blog.utils.WebUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author: 何超
 * @date: 2022/10/13
 */
//token过滤器
@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        //判断请求头是否有token
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        String hasToken = redisCache.getCacheObject(Constants.TOKEN_KEY + token);
        if (!StringUtils.hasText(hasToken)) {
            R result = R.errorResult(HttpCodeEnum.TOKEN_NOT_NULL);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }

        //校验token
        Claims claims = null;
        try {
            claims = JwtUtils.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            R result = R.errorResult(HttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }

        if (JwtUtils.isTokenExpired(claims)) {
            R result = R.errorResult(HttpCodeEnum.TOKEN_NOT_NULL);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }

        String username = claims.getSubject();

        LoginUser loginUser = redisCache.getCacheObject(Constants.LOGIN_KEY + username);

        if (Objects.isNull(loginUser)) {
            //说明登录过期  提示重新登录
            R result = R.errorResult(HttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }

        //将权限信息存入authenticationToken中
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);

    }
}
