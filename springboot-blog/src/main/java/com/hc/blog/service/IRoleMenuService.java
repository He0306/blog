package com.hc.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hc.blog.entity.RoleMenu;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 何超
 * @since 2022-10-02
 */
public interface IRoleMenuService extends IService<RoleMenu> {

    List<String> selectByRoleId(String id);

    void setRoleMenu(String roleId, List<String> menuIds);
}
