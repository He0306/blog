package com.hc.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hc.blog.common.lang.R;
import com.hc.blog.entity.OperationLog;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 何超
 * @since 2022-10-21
 */
public interface IOperationLogService extends IService<OperationLog> {

    R pageList(Integer pageNum, Integer pageSize, String optModule);
}
