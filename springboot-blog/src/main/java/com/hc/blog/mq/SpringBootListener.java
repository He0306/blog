package com.hc.blog.mq;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hc.blog.entity.Article;
import com.hc.blog.entity.doc.ArticleDoc;
import com.hc.blog.entity.vo.ArticleListVo;
import com.hc.blog.mapper.ArticleMapper;
import com.hc.blog.mapper.CategoryMapper;
import com.hc.blog.mapper.UserMapper;
import com.hc.blog.utils.BeanCopyUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: 何超
 * @date: 2022/12/29
 */
@Component
public class SpringBootListener implements ApplicationRunner {

    @Autowired
    RestHighLevelClient client;

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    CategoryMapper categoryMapper;

    private static final String INDEX_STR = "{\n" +
            "  \"mappings\": {\n" +
            "    \"properties\": {\n" +
            "      \"id\":{\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"title\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_max_word\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"content\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_max_word\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"summary\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_max_word\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"categoryId\":{\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"categoryName\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_max_word\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"cover\":{\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"viewCount\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"createTime\":{\n" +
            "        \"type\": \"date\"\n" +
            "      },\n" +
            "      \"nickName\":{\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"all\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_max_word\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";

    /**
     * 启动完成插入es数据
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        //判断es中是否存在blog索引
        GetIndexRequest getIndexRequest = new GetIndexRequest("blog");
        boolean exists = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        if (exists){
            //存在，则删除原有数据
            DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest("blog");
            deleteByQueryRequest.setQuery(QueryBuilders.matchAllQuery());
            client.deleteByQuery(deleteByQueryRequest,RequestOptions.DEFAULT);
        }else {
            CreateIndexRequest createIndexRequest = new CreateIndexRequest("blog");
            createIndexRequest.source(INDEX_STR, XContentType.JSON);
            client.indices().create(createIndexRequest,RequestOptions.DEFAULT);
        }
        //查询已发布的文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus,true);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        for (Article article : articles) {
            article.setNickName(userMapper.selectById(article.getUserId()).getNickName());
            article.setCategoryName(categoryMapper.selectById(article.getCategoryId()).getCategoryName());
        }
        List<ArticleListVo> articleListVoList = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);

        //插入数据
        BulkRequest bulkRequest = new BulkRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        for (ArticleListVo articleListVo : articleListVoList) {
            ArticleDoc articleDoc = new ArticleDoc(articleListVo);
            IndexRequest indexRequest = new IndexRequest("blog").id(articleListVo.getId());
            String json = objectMapper.writeValueAsString(articleDoc);
            indexRequest.source(json,XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        client.bulk(bulkRequest,RequestOptions.DEFAULT);
    }
}
