package com.hc.blog.common.constants;

/**
 * @author: 何超
 * @date: 2022/10/13
 */
public class Constants {

    //redis中login键
    public static final String LOGIN_KEY = "login:";

    //默认密码
    public static final String DEFAULT_PASSWORD = "123456";

    //默认角色
    public static final String DEFAULT_ROLE = "2";

    //redis中token键
    public static final String TOKEN_KEY = "token:";

    //标签的状态为正常
    public static final Integer STATUS_NORMAL = 1;

    //友链为1为正常
    public static final Integer STATUS_LINK = 1;

    //评论 类型0为文章评论
    public static final Integer ARTICLE_COMMENT = 0;

    //评论 类型1为友链评论
    public static final Integer LINK_COMMENT = 1;
}
