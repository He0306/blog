package com.hc.blog.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: 何超
 * @date: 2022/11/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddArticleDto {

    private String id;

    //标题
    private String title;

    //文章内容
    private String content;

    //文章摘要
    private String summary;

    //所属分类id
    private String categoryId;

    //缩略图
    private String cover;

    //是否置顶（0否，1是）
    private Integer isTop;

    //状态（0已发布，1草稿）
    private Integer status;

    //访问量
    private Integer viewCount;
    //标签
    private List<String> tags;
}
