package com.hc.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hc.blog.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 何超
 * @since 2022-10-23
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}
