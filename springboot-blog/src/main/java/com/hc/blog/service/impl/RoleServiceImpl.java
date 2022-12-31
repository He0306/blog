package com.hc.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hc.blog.common.lang.R;
import com.hc.blog.entity.Role;
import com.hc.blog.mapper.RoleMapper;
import com.hc.blog.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 何超
 * @since 2022-10-02
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public R pageList(Integer pageNum, Integer pageSize, String name) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(name)) {
            queryWrapper.like(Role::getRoleName, name);
        }
        Page<Role> page = new Page<>(pageNum, pageSize);
        Page<Role> rolePage = roleMapper.selectPage(page, queryWrapper);
        return R.okResult(rolePage);
    }
}
