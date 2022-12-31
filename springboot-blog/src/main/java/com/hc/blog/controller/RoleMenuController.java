package com.hc.blog.controller;

import com.hc.blog.annotation.OptLog;
import com.hc.blog.common.lang.R;
import com.hc.blog.service.IRoleMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.hc.blog.common.constants.OptTypeConst.BIND_MENU_ROLE;

/**
 * @author: 何超
 * @date: 2022/10/15
 */
@Api(tags = "角色菜单模块")
@RestController
@RequestMapping("/admin/roleMenu")
public class RoleMenuController {

    @Autowired
    IRoleMenuService roleMenuService;

    /**
     * 查询角色菜单表中绑定的菜单ID
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "查询角色菜单表中绑定的菜单ID")
    @GetMapping("/{id}")
    public R getRoleMenuById(@PathVariable("id") String id) {
        return R.okResult(roleMenuService.selectByRoleId(id));
    }

    /**
     * 根据角色ID 和菜单集合ID 进行绑定角色菜单关系
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID
     * @return
     */
    @OptLog(optType = BIND_MENU_ROLE)
    @ApiOperation(value = "根据角色ID和菜单集合ID进行绑定角色菜单关系")
    @PostMapping("/{roleId}")
    public R roleMenu(@PathVariable("roleId") String roleId,
                      @RequestBody List<String> menuIds) {
        roleMenuService.setRoleMenu(roleId, menuIds);
        return R.okResult();
    }
}
