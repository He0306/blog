package com.hc.blog.scheduling;

import com.hc.blog.config.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author: 何超
 * @date: 2022/12/20
 */
@Component
@Slf4j
public class RedisRemove {

    private static final String KEY = "0";

    @Autowired
    RedisCache redisCache;

    /**
     * 2个小时执行一次
     */
    @Scheduled(cron = "0 0 0/2 * * ? ")
    public void remove(){
        redisCache.deleteObject(KEY);
        log.info("定时任务启动");
    }
}
