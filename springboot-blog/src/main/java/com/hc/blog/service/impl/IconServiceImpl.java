package com.hc.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hc.blog.common.lang.R;
import com.hc.blog.entity.Icon;
import com.hc.blog.mapper.IconMapper;
import com.hc.blog.service.IIconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 何超
 * @since 2022-10-13
 */
@Service
public class IconServiceImpl extends ServiceImpl<IconMapper, Icon> implements IIconService {

    @Autowired
    IconMapper iconMapper;

    @Cacheable(value = "pageListIcon", key = "#pageNum+" + "'_'+#pageSize+" + "'_'+#name")
    @Override
    public R listPage(Integer pageNum, Integer pageSize, String name) {
        LambdaQueryWrapper<Icon> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(name)) {
            queryWrapper.like(Icon::getName, name);
        }
        Page<Icon> page = new Page<>(pageNum, pageSize);
        Page<Icon> iconPage = iconMapper.selectPage(page, queryWrapper);
        return R.okResult(iconPage);
    }
}
