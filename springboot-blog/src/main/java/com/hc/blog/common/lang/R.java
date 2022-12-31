package com.hc.blog.common.lang;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 统一返回结果
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class R<T> implements Serializable {

    private Integer code;
    private String msg;
    private T data;

    public R() {
        this.code = HttpCodeEnum.SUCCESS.getCode();
        this.msg = HttpCodeEnum.SUCCESS.getMsg();
    }

    public R(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public R(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public R(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static R errorResult(int code, String msg) {
        R result = new R();
        return result.error(code, msg);
    }

    public static R okResult() {
        R result = new R();
        return result;
    }

    public static R okResult(int code, String msg) {
        R result = new R();
        return result.ok(code, null, msg);
    }

    public static R okResult(Object data) {
        R result = setHttpCodeEnum(HttpCodeEnum.SUCCESS, HttpCodeEnum.SUCCESS.getMsg());
        if (data != null) {
            result.setData(data);
        }
        return result;
    }

    public static R errorResult(HttpCodeEnum enums) {
        return setHttpCodeEnum(enums, enums.getMsg());
    }

    public static R errorResult(HttpCodeEnum enums, String msg) {
        return setHttpCodeEnum(enums, msg);
    }

    public static R setHttpCodeEnum(HttpCodeEnum enums) {
        return okResult(enums.getCode(), enums.getMsg());
    }

    private static R setHttpCodeEnum(HttpCodeEnum enums, String msg) {
        return okResult(enums.getCode(), msg);
    }

    public R<?> error(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }

    public R<?> ok(Integer code, T data) {
        this.code = code;
        this.data = data;
        return this;
    }

    public R<?> ok(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        return this;
    }

    public R<?> ok(T data) {
        this.data = data;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
