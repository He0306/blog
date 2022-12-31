package com.hc.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hc.blog.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    List<String> selectByRoleId(@Param("roleId") String roleId);

    void deleteByRoleId(@Param("roleId") String roleId);
}
