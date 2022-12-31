package com.hc.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hc.blog.common.lang.R;
import com.hc.blog.entity.Tag;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 何超
 * @since 2022-10-23
 */
public interface ITagService extends IService<Tag> {

    R pageList(Integer pageNum, Integer pageSize, String tagName);

    /**
     * 查询所有便签名称
     *
     * @return
     */
    R selectTagName();
}
