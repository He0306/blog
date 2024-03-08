package com.hc.blog.controller;

import com.hc.blog.annotation.OptLog;
import com.hc.blog.common.lang.R;
import com.hc.blog.entity.Icon;
import com.hc.blog.service.IIconService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.hc.blog.common.constants.OptTypeConst.*;

/**
 * @author: 何超
 * @date: 2022/10/14
 */
@Api(tags = "图标模块")
@RestController
@RequestMapping("/admin/icon")
public class IconController {

    @Autowired
    IIconService iconService;

    /**
     * 分页查询全部数据
     *
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @OptLog(optType = SELECT)
    @ApiOperation(value = "分页查询全部图标")
    @PreAuthorize("hasAuthority('sys:icon:list')")
    @GetMapping("/page")
    public R page(@RequestParam Integer pageNum,
                  @RequestParam Integer pageSize,
                  @RequestParam String name) {
        return iconService.listPage(pageNum, pageSize, name);
    }

    /**
     * 新增或修改
     *
     * @param icon
     * @return
     */
    @CacheEvict(value = "pageListIcon", allEntries = true)
    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "新增或修改")
    @PreAuthorize("hasAnyAuthority('sys:icon:update,sys:icon:add')")
    @PostMapping("/saveOrUpdate")
    public R saveOrUpdate(@RequestBody Icon icon) {
        return R.okResult(iconService.saveOrUpdate(icon));
    }

    /**
     * 根据id集合批量删除
     *
     * @param ids
     * @return
     */
    @CacheEvict(value = "pageListIcon", allEntries = true)
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "根据ID批量删除")
    @PreAuthorize("hasAuthority('sys:icon:deleteBatch')")
    @PostMapping("/delete/batch")
    public R deleteBatch(@RequestBody List<String> ids) {
        iconService.removeBatchByIds(ids);
        return R.okResult();
    }

    /**
     * 根据id删除单个
     *
     * @param id
     * @return
     */
    @CacheEvict(value = "pageListIcon", allEntries = true)
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "根据ID删除")
    @PreAuthorize("hasAuthority('sys:icon:deleteById')")
    @DeleteMapping("/{id}")
    public R deleteIconById(@PathVariable("id") String id) {
        iconService.removeById(id);
        return R.okResult();
    }

    @OptLog(optType = SELECT)
    @ApiOperation(value = "查询全部")
    @GetMapping("/getAll")
    public R getAll() {
        return R.okResult(iconService.list());
    }
}
