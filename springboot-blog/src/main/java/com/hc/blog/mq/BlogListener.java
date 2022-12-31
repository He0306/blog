package com.hc.blog.mq;

import com.alibaba.fastjson.JSON;
import com.hc.blog.common.constants.MqConstants;
import com.hc.blog.entity.Article;
import com.hc.blog.entity.doc.ArticleDoc;
import com.hc.blog.entity.vo.ArticleListVo;
import com.hc.blog.mapper.ArticleMapper;
import com.hc.blog.mapper.CategoryMapper;
import com.hc.blog.mapper.UserMapper;
import com.hc.blog.utils.BeanCopyUtils;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author: 何超
 * @date: 2022/12/29
 */
@Component
public class BlogListener {

    @Autowired
    RestHighLevelClient client;

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @RabbitListener(queues = MqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate(String id) throws IOException {
        Article article = articleMapper.selectById(id);
        article.setNickName(userMapper.selectById(article.getUserId()).getNickName());
        article.setCategoryName(categoryMapper.selectById(article.getCategoryId()).getCategoryName());
        ArticleListVo articleListVo = BeanCopyUtils.copyBean(article, ArticleListVo.class);
        ArticleDoc articleDoc = new ArticleDoc(articleListVo);
        IndexRequest indexRequest = new IndexRequest("blog").id(id);
        indexRequest.source(JSON.toJSONString(articleDoc), XContentType.JSON);
        client.index(indexRequest,RequestOptions.DEFAULT);
    }

    @RabbitListener(queues = MqConstants.BLOG_DELETE_QUEUE)
    public void listenBlogDelete(List<String> ids) throws IOException {
        for (String id : ids) {
            DeleteRequest request = new DeleteRequest("blog");
            request.id(id);
            client.delete(request, RequestOptions.DEFAULT);
        }
    }
}
