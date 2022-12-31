package com.hc.blog.mapper;

import com.hc.blog.entity.mongo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author: 何超
 * @date: 2022/12/10
 */
public interface CommentMapper extends MongoRepository<Comment, String> {

}
