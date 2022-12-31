package com.hc.blog.handler.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * 自动填充字段
 */
@Configuration
public class MyMateObjectHandler implements MetaObjectHandler {

    //插入填充
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("create_time", new Date(), metaObject);
    }

    //更新填充
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("create_time", new Date(), metaObject);
        this.setFieldValByName("update_time", new Date(), metaObject);
    }
}
