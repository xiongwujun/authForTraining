package com.gdhengdian.auth.common;


/**
 * 统一响应结构体
 *
 * @author junmov guoyancheng@junmov.cn
 * @date 2018-10-10
 */
public class AuthMessage {

    private Integer status;

    private String message;

    private String accessToken;

    public AuthMessage() {
    }

    public static AuthMessage badRequest() {
        return new AuthMessage(HttpStateEnum.BAD_REQUEST, null);
    }

    public static AuthMessage success(String accessToken) {
        return new AuthMessage(HttpStateEnum.SUCCESS, accessToken);
    }

    public AuthMessage(HttpStateEnum httpState, String accessToken) {
        this.status = httpState.getStatus();
        this.message = httpState.getMessage();
        this.accessToken = accessToken;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "AuthMessage{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
