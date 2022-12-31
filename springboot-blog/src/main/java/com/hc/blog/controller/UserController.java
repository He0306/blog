package com.hc.blog.controller;

import com.hc.blog.annotation.OptLog;
import com.hc.blog.common.lang.R;
import com.hc.blog.entity.User;
import com.hc.blog.entity.dto.UpdatePwdDto;
import com.hc.blog.service.*;
import com.hc.blog.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.hc.blog.common.constants.OptTypeConst.*;


/**
 * @author: 何超
 * @date: 2022/10/14
 */
@Api(tags = "用户模块")
@RestController
@RequestMapping("/admin/user")
@Slf4j
public class UserController {

    @Autowired
    IUserService userService;

    @Autowired
    IMenuService menuService;

    @Autowired
    IRoleService roleService;

    @Autowired
    IUserRoleService userRoleService;

    @Autowired
    IRoleMenuService roleMenuService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 前台获取个人消息
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "前台获取个人消息")
    @GetMapping("/userInfo")
    public R userInfo(@RequestParam String userId) {
        return R.okResult(userService.getById(userId));
    }

    /**
     * 获取当前登录的用户
     *
     * @return
     */
    @ApiOperation(value = " 获取当前登录的用户的权限和菜单信息")
    @GetMapping("/getInfo")
    public R getUserById() {
        return menuService.getUserInfo();
    }

    /**
     * 修改密码
     *
     * @param updatePwdDto
     * @return
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改密码")
    @PostMapping("/updatePassword")
    public R updatePassword(@Valid @RequestBody UpdatePwdDto updatePwdDto) {
        return userService.updatePassword(updatePwdDto);
    }

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping("/getUserInfo")
    public R getUserInfo() {
        return userService.getUserInfo();
    }

    /**
     * 修改个人信息
     *
     * @param user
     * @return
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改个人信息")
    @PostMapping("/updateUser")
    public R updateUser(@RequestBody User user) {
        return R.okResult(userService.updateById(user));
    }

    /**
     * 分页查询所有数据
     *
     * @param pageNum
     * @param pageSize
     * @param username
     * @param email
     * @return
     */
    @ApiOperation(value = "分页查询所有数据")
    @PreAuthorize("hasAuthority('sys:user:list')")
    @GetMapping("/page")
    public R pageList(@RequestParam Integer pageNum,
                      @RequestParam Integer pageSize,
                      @RequestParam String username,
                      @RequestParam String email) {
        return userService.pageList(pageNum, pageSize, username, email);
    }

    /**
     * 添加
     *
     * @param user
     * @return
     */
    @OptLog(optType = SAVE)
    @ApiOperation(value = "添加")
    @PreAuthorize("hasAuthority('sys:user:add')")
    @PostMapping("/save")
    public R save(@RequestBody User user) {
        return userService.saveUser(user);
    }

    /**
     * 修改
     *
     * @param user
     * @return
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改")
    @PreAuthorize("hasAuthority('sys:user:update')")
    @PostMapping("/update")
    public R update(@RequestBody User user) {
        return userService.updateUser(user);
    }

    /**
     * 重置密码
     *
     * @param id
     * @return
     */
    @OptLog(optType = REST_PASSWORD)
    @ApiOperation(value = "重置密码")
    @PreAuthorize("hasAuthority('sys:user:restPassword')")
    @GetMapping("/resetPassword/{id}")
    public R resetPassword(@PathVariable("id") String id) {
        return userService.resetPassword(id);
    }

    /**
     * 根据ID删除
     *
     * @param id
     * @return
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "根据ID删除")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    @DeleteMapping("/{id}")
    public R deleteUserById(@PathVariable("id") String id) {
        if (id.equals(SecurityUtils.getUserId())) {
            return R.errorResult(500, "不能删除当前登录的用户");
        }
        return userService.deleteUserById(id);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "根据ID批量删除")
    @PreAuthorize("hasAuthority('sys:user:deleteBatch')")
    @PostMapping("/delete/batch")
    public R deleteBatch(@RequestBody List<String> ids) {
        return userService.deleteUserBatch(ids);
    }

    @ApiOperation(value = "根据ID查询")
    @GetMapping("/{id}")
    public R getUserById(@PathVariable("id") String id) {
        return userService.getUserById(id);
    }
}
