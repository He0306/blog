package com.hc.blog.service;

import com.hc.blog.common.lang.R;
import com.hc.blog.entity.mongo.Comment;

/**
 * @author: 何超
 * @date: 2022/12/10
 */
public interface ICommentService {

    /**
     * 分页查询文章评论
     *
     * @param articleComment
     * @param pageSize
     * @param pageNum
     * @param articleId
     * @return
     */
    R commentList(Integer articleComment, Integer pageSize, Integer pageNum, String articleId);

    /**
     * 发表评论
     *
     * @param comment
     * @return
     */
    R addComment(Comment comment);

}
