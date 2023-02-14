package com.hc.blog.sensitive;

import com.baidu.aip.contentcensor.AipContentCensor;

/**
 * @author: 何超
 * @date: 2022/12/22
 */
public class BaiDuConfig {

    private static final String APP_ID = "29233699";

    private static final String API_KEY = "Om45oOrv36B8xWRUAuPv64MV";

    private static final String SECRET_KEY = "2KzYNDVyKo3fgXkHMsnpSIltW18eq245";

    public static final AipContentCensor client = new AipContentCensor(APP_ID,API_KEY,SECRET_KEY);


}
