package com.hc.blog.controller;

import com.hc.blog.annotation.OptLog;
import com.hc.blog.common.lang.R;
import com.hc.blog.service.IOperationLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.hc.blog.common.constants.OptTypeConst.REMOVE;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 何超
 * @since 2022-10-21
 */
@Api(tags = "日志模块")
@RestController
@RequestMapping("/admin/optLog")
@Slf4j
public class OperationLogController {

    @Autowired
    IOperationLogService operationLogService;

    /**
     * 分页查询全部数据
     *
     * @param pageNum
     * @param pageSize
     * @param optModule
     * @return
     */
    @ApiOperation(value = "分页查询全部数据")
    @GetMapping("/page")
    public R pageList(@RequestParam Integer pageNum,
                      @RequestParam Integer pageSize,
                      @RequestParam String optModule) {
        return operationLogService.pageList(pageNum, pageSize, optModule);
    }

    /**
     * 根据ID删除
     *
     * @param id
     * @return
     */
    @OptLog(optType = REMOVE)
    @ApiOperation(value = "根据ID删除")
    @DeleteMapping("/{id}")
    public R deleteById(@PathVariable("id") String id) {
        return R.okResult(operationLogService.removeById(id));
    }

    /**
     * 根据ID批量删除
     *
     * @param ids
     * @return
     */
    @OptLog(optType = REMOVE)
    @ApiOperation(value = "根据ID批量删除")
    @PostMapping("/delete/batch")
    public R deleteBatch(@RequestBody List<String> ids) {
        return R.okResult(operationLogService.removeBatchByIds(ids));
    }

}

