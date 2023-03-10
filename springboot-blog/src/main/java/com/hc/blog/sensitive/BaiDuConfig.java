package com.hc.blog.sensitive;

import com.baidu.aip.contentcensor.AipContentCensor;

/**
 * @author: 何超
 * @date: 2022/12/22
 */
public class BaiDuConfig {

    private static final String APP_ID = "";

    private static final String API_KEY = "";

    private static final String SECRET_KEY = "";

    public static final AipContentCensor client = new AipContentCensor(APP_ID,API_KEY,SECRET_KEY);


}
