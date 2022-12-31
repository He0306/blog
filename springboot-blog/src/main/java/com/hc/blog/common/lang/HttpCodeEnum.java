package com.hc.blog.common.lang;

/**
 * @author: 何超
 * @date: 2022/10/13
 */
public enum HttpCodeEnum {

    SUCCESS(200, "操作成功"),
    NEED_LOGIN(401, "需要登录后操作"),
    USERNAME_PASSWORD_NO_EXIST(402, "用户名或密码错误"),
    NO_OPERATOR_AUTH(403, "无权限操作"),
    USERNAME_NOT_NULL(405, "账号不能为空"),
    PASSWORD_NOT_NULL(406, "密码不能为空"),
    TOKEN_NOT_NULL(407, "登录已过期，请重新登录"),
    PASSWORD_ATYPISM(408, "密码不一致"),
    USERNAME_EXISTENCE(409, "账号已存在，请换一个试试"),
    FILE_TYPE_ERROR(410, "文件上传失败"),
    CONFIRMPASSWORD(411, "两次输入的密码不一致"),
    OLDPASSWORD(412, "旧密码不正确"),
    COMMTENT_NOT_NULL(413, "评论不能为空"),
    COMMENT_NON_COMPLIANCE(414,"评论违规，请重新评论"),
    IMG_NON_COMPLIANCE(415,"图片违规，请重新上传图片"),
    SYSTEM_ERROR(500, "系统错误请联系管理员");


    int code;

    String msg;

    HttpCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
