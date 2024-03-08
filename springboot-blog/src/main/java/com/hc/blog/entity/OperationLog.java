package com.hc.blog.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author 何超
 * @since 2022-10-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("operation_log")
@ApiModel(value = "operation_log对象", description = "")
public class OperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Excel(name = "ID")
    @ApiModelProperty("ID")
    private String id;

    @Excel(name = "操作模块")
    @ApiModelProperty("操作模块")
    private String optModule;

    @Excel(name = "操作类型")
    @ApiModelProperty("操作类型")
    private String optType;

    @Excel(name = "操作URL")
    @ApiModelProperty("操作url")
    private String optUrl;

    @Excel(name = "操作方法")
    @ApiModelProperty("操作方法")
    private String optMethod;

    @Excel(name = "操作描述")
    @ApiModelProperty("操作描述")
    private String optDesc;

    @Excel(name = "请求参数")
    @ApiModelProperty("请求参数")
    private String requestParam;

    @Excel(name = "请求方式")
    @ApiModelProperty("请求方式")
    private String requestMethod;

    @Excel(name = "返回数据")
    @ApiModelProperty("返回数据")
    private String responseData;

    @Excel(name = "操作人ID")
    @ApiModelProperty("用户id")
    private String userId;

    @Excel(name = "操作人昵称")
    @ApiModelProperty("用户昵称")
    private String nickName;

    @Excel(name = "操作IP地址")
    @ApiModelProperty("操作ip")
    private String ipAddress;

    @Excel(name = "操作地址")
    @ApiModelProperty("操作地址")
    private String ipSource;

    @Excel(name = "操作时间", exportFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;


}
