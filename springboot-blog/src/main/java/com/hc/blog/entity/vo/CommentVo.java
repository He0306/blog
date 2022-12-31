package com.hc.blog.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author: 何超
 * @date: 2022/12/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String userId;

    //文章id
    private String articleId;

    //根评论id
    private String rootId;

    //评论内容
    private String content;

    //所回复的目标评论的userid
    private String toCommentUserId;

    private String toCommentUserName;

    //回复目标评论id
    private String toCommentId;

    private Date createTime;

    private String username;

    private String avatar;

    private List<CommentVo> children;
}

