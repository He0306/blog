package com.hc.blog.common.constants;

/**
 * @author: 何超
 * @date: 2022/12/29
 */
public class MqConstants {

    /**
     * 交换机名称
     */
    public static final String BLOG_EXCHANGE = "blog.topic";

    /**
     * 新增或修改队列
     */
    public static final String BLOG_INSERT_QUEUE = "blog.insert.queue";

    /**
     * 删除队列
     */
    public static final String BLOG_DELETE_QUEUE = "blog.delete.queue";

    /**
     * 绑定key
     */
    public static final String BLOG_INSERT_KEY = "blog.insert";

    /**
     * 绑定key
     */
    public static final String BLOG_DELETE_KEY = "blog.delete";

}
