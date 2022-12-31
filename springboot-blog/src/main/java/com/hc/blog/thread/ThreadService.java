package com.hc.blog.thread;

import com.hc.blog.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author: 何超
 * @date: 2022/12/20
 */
@Component
public class ThreadService {

    @Autowired
    ArticleMapper articleMapper;

    /**
     * 异步在线程池更新数据
     * @param id
     */
    @Async("executor")
    public void updateArticleViewCount(String id) {
        articleMapper.updateViewCount(id);
    }
}
