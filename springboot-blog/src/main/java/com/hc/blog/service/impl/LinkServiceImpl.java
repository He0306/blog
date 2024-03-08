package com.hc.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hc.blog.common.constants.Constants;
import com.hc.blog.common.lang.R;
import com.hc.blog.entity.Link;
import com.hc.blog.mapper.LinkMapper;
import com.hc.blog.service.ILinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 何超
 * @since 2022-11-27
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements ILinkService {

    @Autowired
    LinkMapper linkMapper;

    @Cacheable(value = "pageListLink", key = "#pageNum+" + "'_'+#pageSize+" + "'_'+#name")
    @Override
    public R pageList(Integer pageNum, Integer pageSize, String name) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(name)) {
            queryWrapper.eq(Link::getName, name);
        }
        Page<Link> page = new Page<>(pageNum, pageSize);
        Page<Link> linkPage = linkMapper.selectPage(page, queryWrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("total", linkPage.getTotal());
        map.put("linkList", linkPage.getRecords());
        return R.okResult(map);
    }

    /**
     * 前台查询友链
     *
     * @return
     */
    @Cacheable(value = "list.link", key = "#root.methodName")
    @Override
    public R getAllLink() {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, Constants.STATUS_LINK);
        List<Link> links = linkMapper.selectList(queryWrapper);
        return R.okResult(links);
    }
}
