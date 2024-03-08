package com.hc.blog.controller;

import com.hc.blog.annotation.OptLog;
import com.hc.blog.common.lang.R;
import com.hc.blog.entity.Menu;
import com.hc.blog.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.hc.blog.common.constants.OptTypeConst.*;

/**
 * @author: 何超
 * @date: 2022/10/15
 */
@Api(tags = "菜单模块")
@RestController
@RequestMapping("/admin/menu")
public class MenuController {


    @Autowired
    IMenuService menuService;

    /**
     * 查询树型菜单
     *
     * @param name
     * @return
     */
    @OptLog(optType = SELECT)
    @ApiOperation(value = "树型结构显示菜单")
    @GetMapping
    public R findAll(@RequestParam(defaultValue = "") String name) {
        return R.okResult(menuService.findMenus(name));
    }

    /**
     * 查询所有菜单id的集合
     *
     * @return
     */
    @OptLog(optType = SELECT)
    @ApiOperation(value = "查询所有菜单id的集合")
    @GetMapping("/ids")
    public R findAllIds() {
        return R.okResult(menuService.list().stream().map(Menu::getId));
    }

    /**
     * 根据ID删除
     *
     * @param id
     * @return
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "根据ID删除")
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    @DeleteMapping("/{id}")
    public R deleteByMenuId(@PathVariable("id") String id) {
        return R.okResult(menuService.removeById(id));
    }

    /**
     * 查询所有菜单
     *
     * @return
     */
    @ApiOperation(value = "查询所有菜单")
    @GetMapping("/findAllParentMenu")
    public R findAll() {
        return R.okResult(menuService.list());
    }

    /**
     * 新增或修改
     *
     * @param menu
     * @return
     */
    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "新增或修改")
    @PreAuthorize("hasAnyAuthority('sys:menu:update,sys:menu:add')")
    @PostMapping("/save")
    public R saveOrUpdate(@RequestBody Menu menu) {
        menuService.saveOrUpdate(menu);
        return R.okResult();
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @OptLog(optType = UPDATE)
    @ApiOperation("根据ID批量删除")
    @PreAuthorize("hasAuthority('sys:menu:deleteBatch')")
    @PostMapping("/delete/batch")
    public R deleteBatch(@RequestBody List<String> ids) {
        return R.okResult(menuService.removeBatchByIds(ids));
    }

}
