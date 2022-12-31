package com.hc.blog.controller;


import com.hc.blog.annotation.OptLog;
import com.hc.blog.common.lang.R;
import com.hc.blog.entity.Tag;
import com.hc.blog.service.ITagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.hc.blog.common.constants.OptTypeConst.REMOVE;
import static com.hc.blog.common.constants.OptTypeConst.SAVE_OR_UPDATE;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 何超
 * @since 2022-10-23
 */
@RestController
@RequestMapping("/admin/tag")
@Api(tags = "标签模块")
public class TagController {

    @Autowired
    ITagService tagService;

    /**
     * 分页查询全部数据
     *
     * @param pageNum
     * @param pageSize
     * @param tagName
     * @return
     */
    @ApiOperation(value = "分页查询全部数据")
    @GetMapping("/page")
    public R pageList(@RequestParam Integer pageNum,
                      @RequestParam Integer pageSize,
                      @RequestParam String tagName) {
        return tagService.pageList(pageNum, pageSize, tagName);
    }

    /**
     * 新增或修改
     *
     * @param tag
     * @return
     */
    @CacheEvict(value = "pageListTag",allEntries = true)
    @ApiOperation(value = "新增或修改")
    @OptLog(optType = SAVE_OR_UPDATE)
    @PostMapping("/saveOrUpdate")
    public R saveOrUpdate(@RequestBody Tag tag) {
        return R.okResult(tagService.saveOrUpdate(tag));
    }

    /**
     * 根据ID删除
     */
    @CacheEvict(value = "pageListTag",allEntries = true)
    @OptLog(optType = REMOVE)
    @ApiOperation(value = "根据ID删除")
    @DeleteMapping("/{id}")
    public R deleteByTagId(@PathVariable("id") String id) {
        return R.okResult(tagService.removeById(id));
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @CacheEvict(value = "pageListTag",allEntries = true)
    @OptLog(optType = REMOVE)
    @ApiOperation(value = "批量删除")
    @PostMapping("/delete/batch")
    public R deleteBatch(@RequestBody List<String> ids) {
        return R.okResult(tagService.removeBatchByIds(ids));
    }

    /**
     * 查询全部
     */
    @ApiOperation(value = "查询全部")
    @GetMapping("/list")
    public R list() {
        return R.okResult(tagService.list());
    }

}

