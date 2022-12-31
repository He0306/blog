package com.hc.blog.controller;


import com.hc.blog.annotation.OptLog;
import com.hc.blog.common.lang.R;
import com.hc.blog.entity.Category;
import com.hc.blog.service.ICategoryService;
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
 * @since 2022-10-23
 */
@Api(tags = "分类模块")
@RestController
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    ICategoryService categoryService;

    /**
     * 分类分页查询全部
     *
     * @param pageNum
     * @param pageSize
     * @param categoryName
     * @return
     */
    @ApiOperation(value = "分类分页查询全部")
    @GetMapping("/page")
    public R pageList(@RequestParam Integer pageNum,
                      @RequestParam Integer pageSize,
                      @RequestParam String categoryName) {
        return categoryService.pageList(pageNum, pageSize, categoryName);
    }

    /**
     * 新增或修改
     *
     * @param category
     * @return
     */
    @CacheEvict(value = "pageListCategory", allEntries = true)
    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "新增或修改")
    @PostMapping("/saveOrUpdate")
    public R saveOrUpdate(@RequestBody Category category) {
        return R.okResult(categoryService.saveOrUpdate(category));
    }

    /**
     * 根据ID删除
     *
     * @param id
     * @return
     */
    @CacheEvict(value = "pageListCategory", allEntries = true)
    @ApiOperation(value = "根据ID删除")
    @OptLog(optType = REMOVE)
    @DeleteMapping("/{id}")
    public R deleteCategoryById(@PathVariable("id") String id) {
        return R.okResult(categoryService.removeById(id));
    }

    /**
     * 根据ID批量删除
     *
     * @param ids
     * @return
     */
    @CacheEvict(value = "pageListCategory", allEntries = true)
    @OptLog(optType = BATCH_REMOVE)
    @PostMapping("/delete/batch")
    @ApiOperation(value = "根据ID批量删除")
    public R deleteBatch(@RequestBody List<String> ids) {
        return R.okResult(categoryService.removeBatchByIds(ids));
    }

    /**
     * 查询全部
     *
     * @return
     */
    @ApiOperation(value = "查询全部")
    @GetMapping("/list")
    public R list() {
        return R.okResult(categoryService.list());
    }

}

