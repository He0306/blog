package com.hc.blog.controller;

import com.hc.blog.annotation.OptLog;
import com.hc.blog.common.lang.R;
import com.hc.blog.entity.Role;
import com.hc.blog.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.hc.blog.common.constants.OptTypeConst.SAVE_OR_UPDATE;
import static com.hc.blog.common.constants.OptTypeConst.UPDATE;

/**
 * @author: 何超
 * @date: 2022/10/15
 */
@Api(tags = "角色模块")
@RestController
@RequestMapping("/admin/role")
public class RoleController {

    @Autowired
    IRoleService roleService;

    /**
     * 分页查询全部数据
     *
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @ApiOperation(value = "分页查询全部数据")
    @PreAuthorize("hasAuthority('sys:role:list')")
    @GetMapping("/page")
    public R pageList(@RequestParam Integer pageNum,
                      @RequestParam Integer pageSize,
                      @RequestParam String name) {
        return roleService.pageList(pageNum, pageSize, name);
    }

    /**
     * 新增或修改
     *
     * @param role
     * @return
     */
    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "新增或修改")
    @PreAuthorize("hasAnyAuthority('sys:role:update,sys:role:add')")
    @PostMapping("/saveOrUpdate")
    public R saveOrUpdate(@RequestBody Role role) {
        return R.okResult(roleService.saveOrUpdate(role));
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "根据ID删除")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    @DeleteMapping("/{id}")
    public R deleteRoleById(@PathVariable("id") String id) {
        return R.okResult(roleService.removeById(id));
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "根据ID批量删除")
    @PreAuthorize("hasAuthority('sys:role:deleteBatch')")
    @PostMapping("/delete/batch")
    public R deleteBatch(@RequestBody List<String> ids) {
        return R.okResult(roleService.removeBatchByIds(ids));
    }

    /**
     * 查询所有角色
     *
     * @return
     */
    @ApiOperation(value = "查询所有角色")
    @GetMapping("/all")
    public R all() {
        return R.okResult(roleService.list());
    }
}
