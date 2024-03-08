package com.hc.blog.common.exception;

import com.hc.blog.common.lang.HttpCodeEnum;
import lombok.Data;

@Data
public class ServiceException extends RuntimeException {

    private Integer code;

    private String msg;


    public ServiceException(HttpCodeEnum httpCodeEnum) {
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }
}
