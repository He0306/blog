package com.hc.blog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 何超
 * @since 2022-10-23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("h_article")
@ApiModel(value = "Article对象", description = "")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("文章ID")
    private String id;

    @ApiModelProperty("文章标题")
    private String title;

    @ApiModelProperty("文章内容")
    private String content;

    @ApiModelProperty("文章摘要")
    private String summary;

    @ApiModelProperty("文章分类id")
    private String categoryId;

    @ApiModelProperty("文章缩略图")
    private String cover;

    @ApiModelProperty("是否置顶(0否，1是)")
    private Integer isTop;

    @ApiModelProperty("状态(1已发布，0未发布)")
    private Boolean status;

    @ApiModelProperty("访问量")
    private Integer viewCount;

    @ApiModelProperty("逻辑删除")
    @TableLogic
    private Integer isDelete;

    @ApiModelProperty("发布人ID")
    private String userId;

    @ApiModelProperty("发布人昵称")
    @TableField(exist = false)
    private String nickName;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @TableField(exist = false)
    private String categoryName;

    @TableField(exist = false)
    private List<String> tags;

    public Article(String id, Integer viewCount) {
        this.id = id;
        this.viewCount = viewCount;
    }
}
