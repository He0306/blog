package com.hc.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hc.blog.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 何超
 * @since 2022-10-02
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(String id);

}
