package com.hc.blog.controller;

import com.hc.blog.common.constants.Constants;
import com.hc.blog.common.lang.R;
import com.hc.blog.entity.mongo.Comment;
import com.hc.blog.service.ICommentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: 何超
 * @date: 2022/12/10
 */
@Api(tags = "评论模块")
@RestController
@RequestMapping("/comment")
public class CommentFrontController {

    @Autowired
    ICommentService commentService;

    @GetMapping("/commentList")
    public R commentList(@RequestParam Integer pageSize,
                         @RequestParam Integer pageNum,
                         @RequestParam String articleId) {
        return commentService.commentList(Constants.ARTICLE_COMMENT, pageSize, pageNum, articleId);
    }

    @PostMapping
    public R addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }

    @GetMapping("/linkCommentList")
    public R linkCommentList(Integer pageNum, Integer pageSize) {
        return commentService.commentList(Constants.LINK_COMMENT, pageSize, pageNum, null);
    }
}
