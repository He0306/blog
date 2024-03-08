package com.hc.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hc.blog.common.lang.R;
import com.hc.blog.entity.OperationLog;
import com.hc.blog.mapper.OperationLogMapper;
import com.hc.blog.service.IOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 何超
 * @since 2022-10-21
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements IOperationLogService {

    @Autowired
    OperationLogMapper operationLogMapper;

    @Override
    public R pageList(Integer pageNum, Integer pageSize, String optModule) {
        LambdaQueryWrapper<OperationLog> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(optModule)) {
            queryWrapper.like(OperationLog::getOptModule, optModule);
        }
        Page<OperationLog> page = new Page<>(pageNum, pageSize);
        Page<OperationLog> operationLogPage = operationLogMapper.selectPage(page, queryWrapper);
        return R.okResult(operationLogPage);
    }
}
