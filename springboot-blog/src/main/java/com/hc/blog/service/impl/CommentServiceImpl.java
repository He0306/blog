package com.hc.blog.service.impl;

import com.hc.blog.common.exception.ServiceException;
import com.hc.blog.common.lang.HttpCodeEnum;
import com.hc.blog.common.lang.R;
import com.hc.blog.entity.mongo.Comment;
import com.hc.blog.entity.vo.CommentVo;
import com.hc.blog.entity.vo.PageVo;
import com.hc.blog.mapper.CommentMapper;
import com.hc.blog.sensitive.BaiDuAiCheck;
import com.hc.blog.service.ICommentService;
import com.hc.blog.service.IUserService;
import com.hc.blog.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: 何超
 * @date: 2022/12/10
 */
@Slf4j
@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    IUserService userService;

    /**
     * 分页查询文章评论
     *
     * @param pageSize
     * @param pageNum
     * @param articleId
     * @return
     */
    @Override
    public R commentList(Integer articleComment, Integer pageSize, Integer pageNum, String articleId) {
        Query query = new Query(Criteria.where("root_id").is("-1").and("type").is(articleComment));
        PageRequest page = PageRequest.of(pageNum - 1, pageSize);
        //查询总条数
        long size = mongoTemplate.find(new Query(Criteria.where("type").is(articleComment)), Comment.class).size();
        //分页条件查询
        List<Comment> articleComments = mongoTemplate.find(query.with(page), Comment.class);
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : articleComments) {
            CommentVo commentVo = new CommentVo();
            commentVo.setId(comment.getId());
            commentVo.setUserId(comment.getUserId());
            commentVo.setArticleId(articleId);
            commentVo.setRootId(comment.getRootId());
            commentVo.setContent(comment.getContent());
            commentVo.setToCommentUserId(comment.getToCommentUserId());
            commentVo.setToCommentId(comment.getToCommentId());
            commentVo.setCreateTime(comment.getCreateTime());
            commentVo.setUsername(userService.getById(comment.getUserId()).getNickName());
            commentVo.setAvatar(userService.getById(comment.getUserId()).getAvatar());
            if (!comment.getToCommentUserId().equals("-1")) {
                commentVo.setToCommentUserName(userService.getById(comment.getToCommentUserId()).getNickName());
            }
            commentVo.setChildren(getChildren(comment.getId()));
            commentVoList.add(commentVo);
        }
        return R.okResult(new PageVo(commentVoList, size));
    }

    private List<CommentVo> getChildren(String id) {
        Query query = new Query(Criteria.where("root_id").is(id));
        query.with(Sort.by(Sort.Order.desc("create_time")));
        List<Comment> articleComments = mongoTemplate.find(query, Comment.class);
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment articleComment : articleComments) {
            CommentVo commentVo = new CommentVo();
            commentVo.setUserId(articleComment.getUserId());
            commentVo.setId(articleComment.getId());
            commentVo.setArticleId(articleComment.getArticleId());
            commentVo.setRootId(articleComment.getRootId());
            commentVo.setContent(articleComment.getContent());
            commentVo.setToCommentUserId(articleComment.getToCommentUserId());
            commentVo.setToCommentId(articleComment.getToCommentId());
            commentVo.setCreateTime(articleComment.getCreateTime());
            commentVo.setUsername(userService.getById(articleComment.getUserId()).getNickName());
            commentVo.setAvatar(userService.getById(articleComment.getUserId()).getAvatar());
            if (!articleComment.getToCommentUserId().equals("-1")) {
                commentVo.setToCommentUserName(userService.getById(articleComment.getToCommentUserId()).getNickName());
            }
            commentVoList.add(commentVo);
        }
        return commentVoList;
    }

    /**
     * 发表评论
     *
     * @param comment
     * @return
     */
    @Override
    public R addComment(Comment comment) {
        comment.setUserId(SecurityUtils.getUserId());
        comment.setCreateTime(new Date());
        if (!StringUtils.hasText(comment.getContent())) {
            throw new ServiceException(HttpCodeEnum.COMMTENT_NOT_NULL);
        }
        JSONObject jsonObject = BaiDuAiCheck.checkText(comment.getContent());
        if (jsonObject.get("conclusion").equals("不合规")){
            throw new ServiceException(HttpCodeEnum.COMMENT_NON_COMPLIANCE);
        }
        commentMapper.save(comment);
        return R.okResult();
    }
}
