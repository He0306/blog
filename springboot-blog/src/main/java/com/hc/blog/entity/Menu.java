package com.hc.blog.entity;

import com.baomidou.mybatisplus.annotation.*;
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
 * @since 2022-10-02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_menu")
@ApiModel(value = "Menu对象", description = "")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("父级ID")
    private String pid;

    @ApiModelProperty("权限id")
    private String fid;

    @ApiModelProperty("页面组件路径")
    private String pagePath;

    @ApiModelProperty("权限编码")
    private String perms;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("逻辑删除（1已删除，0未删除）")
    @TableLogic
    private Integer isDelete;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @TableField(exist = false)
    private List<Menu> children;

}
