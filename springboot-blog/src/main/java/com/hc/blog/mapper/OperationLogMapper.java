package com.hc.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hc.blog.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 何超
 * @since 2022-10-21
 */
@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {

}
