package com.hc.blog.controller;


import com.hc.blog.annotation.OptLog;
import com.hc.blog.common.lang.R;
import com.hc.blog.entity.Link;
import com.hc.blog.service.ILinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.hc.blog.common.constants.OptTypeConst.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 何超
 * @since 2022-11-27
 */
@Api(tags = "友链模块")
@RestController
@RequestMapping("/admin/link")
public class LinkController {

    @Autowired
    ILinkService linkService;

    /**
     * 分页查询全部数据
     *
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @OptLog(optType = SELECT)
    @ApiOperation(value = "分页查询全部数据")
    @GetMapping("/page")
    public R page(@RequestParam Integer pageNum,
                  @RequestParam Integer pageSize,
                  @RequestParam String name) {
        return linkService.pageList(pageNum, pageSize, name);
    }

    /**
     * 新增或修改
     *
     * @param link
     * @return
     */
    @CacheEvict(value = "pageListLink",allEntries = true)
    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "新增或修改")
    @PostMapping("/saveOrUpdate")
    public R saveOrUpdate(@RequestBody Link link) {
        return R.okResult(linkService.saveOrUpdate(link));
    }

    /**
     * 根据ID删除
     *
     * @param id
     * @return
     */
    @CacheEvict(value = "pageListLink",allEntries = true)
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "根据ID删除")
    @DeleteMapping("/{id}")
    public R remove(@PathVariable("id") String id) {
        return R.okResult(linkService.removeById(id));
    }

    /**
     * 根据ID批量删除
     *
     * @param ids
     * @return
     */
    @CacheEvict(value = "pageListLink",allEntries = true)
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "根据ID批量删除")
    @PostMapping("/delete/batch")
    public R deleteBatch(@RequestBody List<String> ids) {
        return R.okResult(linkService.removeBatchByIds(ids));
    }

}

