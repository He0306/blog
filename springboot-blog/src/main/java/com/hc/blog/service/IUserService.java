package com.hc.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hc.blog.common.lang.R;
import com.hc.blog.entity.User;
import com.hc.blog.entity.dto.RegisterUserDto;
import com.hc.blog.entity.dto.UpdatePwdDto;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 何超
 * @since 2022-10-02
 */
public interface IUserService extends IService<User> {

    R login(User user);

    R logout();

    R register(RegisterUserDto registerUserDto);

    R pageList(Integer pageNum, Integer pageSize, String username, String email);

    R resetPassword(String id);

    R deleteUserById(String id);

    R saveUser(User user);

    R updateUser(User user);

    R deleteUserBatch(List<String> ids);

    R getUserById(String id);

    R updatePassword(UpdatePwdDto updatePwdDto);

    R getUserInfo();

}
