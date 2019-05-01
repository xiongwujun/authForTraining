package com.gdhengdian.auth.common;

/**
 * @author junmov guoyancheng@junmov.cn
 * @date 2018-10-10
 */
public enum HttpStateEnum {
    /**
     * 成功
     */
    SUCCESS(200, "ok"),

    /**
     * 参数不齐全或参数错误
     */
    BAD_REQUEST(400, "参数不正确"),

    /**
     * 认证失败，没有权限或权限已过期
     */
    UNAUTHORIZED(401, "您未获得访问权限"),
    /**
     * 认证失败，没有权限或权限已过期
     */
    FORBIDDEN(403, "权限不足"),
    /**
     * 账号或密码错误
     */
    USERNAME_OR_PASSWORD_ERROR(402, "您需要输入正确的账号或密码"),
    /**
     * 有重复的数据
     */
    REPEAT_DATA(403, "部分数据已存在"),
    /**
     * 未找到
     */
    NOT_FOUND(404, "服务器没有找到您想要的数据"),
    /**
     * 未知错误
     */
    UNKNOWN_EXCEPTION(500, "网络异常，请稍后再试"),
    /**
     * 文件内容格式不正确
     */
    FILE_FORMAT_ERROR(405, "文件内容格式不正确");


    private Integer status;
    private String message;

    HttpStateEnum(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "HttpStateEnum{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }

}
