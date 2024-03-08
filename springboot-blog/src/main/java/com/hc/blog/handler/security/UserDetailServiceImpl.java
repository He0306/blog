package com.hc.blog.handler.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hc.blog.common.lang.HttpCodeEnum;
import com.hc.blog.entity.User;
import com.hc.blog.mapper.MenuMapper;
import com.hc.blog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author: 何超
 * @date: 2022/10/13
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(queryWrapper);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(HttpCodeEnum.USERNAME_PASSWORD_NO_EXIST.getMsg());
        }

        List<String> list = menuMapper.selectPermsByUserId(user.getId());

        return new LoginUser(user, list);
    }
}
