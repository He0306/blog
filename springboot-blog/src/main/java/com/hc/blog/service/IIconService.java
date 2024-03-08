package com.hc.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hc.blog.common.lang.R;
import com.hc.blog.entity.Icon;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 何超
 * @since 2022-10-13
 */
public interface IIconService extends IService<Icon> {

    R listPage(Integer pageNum, Integer pageSize, String name);
}
