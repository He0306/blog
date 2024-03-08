package com.hc.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hc.blog.common.lang.R;
import com.hc.blog.entity.Menu;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 何超
 * @since 2022-10-02
 */
public interface IMenuService extends IService<Menu> {

    R getUserInfo();

    List<Menu> getRoleMenu(String id);

    List<Menu> findMenus(String name);

}
