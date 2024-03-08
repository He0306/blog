package com.hc.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hc.blog.common.lang.R;
import com.hc.blog.entity.Link;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 何超
 * @since 2022-11-27
 */
public interface ILinkService extends IService<Link> {

    R pageList(Integer pageNum, Integer pageSize, String name);

    /**
     * 前台查询友链
     *
     * @return
     */
    R getAllLink();

}
