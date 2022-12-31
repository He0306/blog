package com.hc.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hc.blog.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 何超
 * @since 2022-10-02
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    String selectByUserId(@Param("userId") String id);

    void userRoleByRoleId(@Param("roleId") String roleId, @Param("userId") String id);

    void deleteByUserId(@Param("userId") String id);
}
