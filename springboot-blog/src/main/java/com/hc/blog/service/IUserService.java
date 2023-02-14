package com.hc.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hc.blog.common.lang.R;
import com.hc.blog.entity.User;
import com.hc.blog.entity.dto.RegisterUserDto;
import com.hc.blog.entity.dto.UpdatePwdDto;
import com.hc.blog.entity.dto.UserRetrieveDto;

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

    /**
     * 登录
     * @param user
     * @return
     */
    R login(User user);

    /**
     * 退出登录
     * @return
     */
    R logout();

    /**
     * 注册
     * @param registerUserDto
     * @return
     */
    R register(RegisterUserDto registerUserDto);

    /**
     * 分页查询全部
     * @param pageNum
     * @param pageSize
     * @param username
     * @param email
     * @return
     */
    R pageList(Integer pageNum, Integer pageSize, String username, String email);

    /**
     * 重置密码
     * @param id
     * @return
     */
    R resetPassword(String id);

    /**
     * 根据ID删除
     * @param id
     * @return
     */
    R deleteUserById(String id);

    /**
     * 新增用户
     * @param user
     * @return
     */
    R saveUser(User user);

    /**
     * 更新用户
     * @param user
     * @return
     */
    R updateUser(User user);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    R deleteUserBatch(List<String> ids);

    /**
     * 更加用户ID查询
     * @param id
     * @return
     */
    R getUserById(String id);

    /**
     * 修改密码
     * @param updatePwdDto
     * @return
     */
    R updatePassword(UpdatePwdDto updatePwdDto);

    /**
     * 获取登录用户信息
     * @return
     */
    R getUserInfo();

    /**
     * 发送验证码
     * @param email
     * @return
     */
    R sendCode(String email);

    /**
     * 找回密码
     * @param dto
     * @return
     */
    R retrievePassword(UserRetrieveDto dto);
}
