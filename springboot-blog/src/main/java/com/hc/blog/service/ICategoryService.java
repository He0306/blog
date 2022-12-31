package com.hc.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hc.blog.common.lang.R;
import com.hc.blog.entity.Category;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 何超
 * @since 2022-10-23
 */
public interface ICategoryService extends IService<Category> {

    R pageList(Integer pageNum, Integer pageSize, String categoryName);

    /**
     * 前台查询状态正常的标签
     *
     * @return
     */
    R getCategoryList();


}
