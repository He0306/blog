package com.hc.blog.entity.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author: 何超
 * @date: 2022/12/10
 */
@Document(collection = "comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    private String id;

    private Integer type;

    @Field(name = "user_id")
    private String userId;

    @Field(name = "article_id")
    private String articleId;

    @Field(name = "root_id")
    private String rootId;

    private String content;

    @Field(name = "to_comment_user_id")
    private String toCommentUserId;

    @Field(name = "to_comment_id")
    private String toCommentId;

    @Field(name = "create_time")
    private Date createTime;
}
