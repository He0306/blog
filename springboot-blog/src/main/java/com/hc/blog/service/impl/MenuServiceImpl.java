package com.hc.blog.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hc.blog.common.lang.R;
import com.hc.blog.entity.Menu;
import com.hc.blog.entity.User;
import com.hc.blog.entity.vo.CurrentUserInfo;
import com.hc.blog.entity.vo.UserInfoVo;
import com.hc.blog.handler.security.LoginUser;
import com.hc.blog.mapper.MenuMapper;
import com.hc.blog.mapper.RoleMapper;
import com.hc.blog.mapper.RoleMenuMapper;
import com.hc.blog.mapper.UserRoleMapper;
import com.hc.blog.service.IMenuService;
import com.hc.blog.utils.BeanCopyUtils;
import com.hc.blog.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 何超
 * @since 2022-10-02
 */
@Service
@Slf4j
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    RoleMenuMapper roleMenuMapper;

    @Override
    public R getUserInfo() {
        //获取当前登录用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限信息
        List<String> permissions = menuMapper.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户id查询角色信息
        List<String> roleKeyList = roleMapper.selectRoleKeyByUserId(loginUser.getUser().getId());
        //获取用户信息
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        //封装数据返回
        CurrentUserInfo currentUserInfo = new CurrentUserInfo(permissions, roleKeyList, userInfoVo);
        return R.okResult(currentUserInfo);
    }

    @Override
    public List<Menu> getRoleMenu(String id) {
        //查询角色用户表
        String roleId = userRoleMapper.selectByUserId(id);
        //根据当前角色查询角色菜单表
        List<String> menuIds = roleMenuMapper.selectByRoleId(roleId);
        //查询出所有菜单
        List<Menu> menus = this.findMenus("");
        List<Menu> roleMenus = new ArrayList<>();
        //筛选出当前用户角色菜单
        for (Menu menu : menus) {
            if (menuIds.contains(menu.getId())) {
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            children.removeIf(child -> !menuIds.contains(child.getId()));
        }
        return roleMenus;
    }

    @Override
    public List<Menu> findMenus(String name) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(name)) {
            queryWrapper.like(Menu::getName, name);
        }
        queryWrapper.orderByAsc(Menu::getSort);
        //查询所有数据
        List<Menu> menus = menuMapper.selectList(queryWrapper);
        //找出pid为null的一级菜单
        List<Menu> collect = menus.stream()
                .filter(menu -> menu.getPid() == null && menu.getFid() == null)
                .collect(Collectors.toList());
        //找出一级菜单的子菜单
        for (Menu menu : collect) {
            //筛选所有数据中pid=父级id的数据
            List<Menu> menus1 = menus.stream().filter(m -> menu.getId().equals(m.getPid())).collect(Collectors.toList());
            menu.setChildren(menus1);
            for (Menu m : menus1) {
                List<Menu> m1 = menus.stream().filter(m2 -> m.getId().equals(m2.getFid())).collect(Collectors.toList());
                m.setChildren(m1);
            }
        }
        return collect;
    }

}
