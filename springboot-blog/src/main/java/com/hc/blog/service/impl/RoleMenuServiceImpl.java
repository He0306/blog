package com.hc.blog.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hc.blog.entity.Menu;
import com.hc.blog.entity.RoleMenu;
import com.hc.blog.mapper.MenuMapper;
import com.hc.blog.mapper.RoleMenuMapper;
import com.hc.blog.service.IRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 何超
 * @since 2022-10-02
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    @Autowired
    RoleMenuMapper roleMenuMapper;

    @Autowired
    MenuMapper menuMapper;

    @Override
    public List<String> selectByRoleId(String id) {
        return roleMenuMapper.selectByRoleId(id);
    }

    @Transactional
    @Override
    public void setRoleMenu(String roleId, List<String> menuIds) {
        //先删除当前角色id所有绑定关系
        roleMenuMapper.deleteByRoleId(roleId);
        //再把前端传过来的菜单id数组绑定到当前的这个角色id上去
        ArrayList<String> menuIdsCopy = CollUtil.newArrayList(menuIds);
        for (String menuId : menuIds) {
            Menu menu = menuMapper.selectById(menuId);
            // 二级菜单 并且传过来的menuId数组里面没有它的父级id
            if (menu.getPid() != null && !menuIdsCopy.contains(menu.getPid())) {
                // 那么我们就得补上这个父级id
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menu.getPid());
                roleMenuMapper.insert(roleMenu);
                menuIdsCopy.add(menu.getPid());
            }
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);
        }
    }
}
