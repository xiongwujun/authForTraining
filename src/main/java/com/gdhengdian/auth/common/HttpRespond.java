package com.gdhengdian.auth.common;

/**
 * 统一响应结构体
 *
 * @author junmov guoyancheng@junmov.cn
 * @date 2018-10-10
 */
public class HttpRespond<T> {

    private Integer status;

    private String message;

    private T data;

    private String accessToken;


    public HttpRespond() {
    }

    public HttpRespond(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public static HttpRespond badRequest() {
        return new HttpRespond(HttpStateEnum.BAD_REQUEST);
    }

    public static HttpRespond unauthorized() {
        return new HttpRespond(HttpStateEnum.UNAUTHORIZED);
    }

    public static HttpRespond notFount() {
        return new HttpRespond(HttpStateEnum.NOT_FOUND);
    }

    public static HttpRespond success() {
        return new HttpRespond(HttpStateEnum.SUCCESS);
    }

    public static HttpRespond authFail(AuthMessage message) {
        return new HttpRespond(message.getStatus(), message.getMessage());
    }

    public HttpRespond(HttpStateEnum httpStateEnum) {
        this.status = httpStateEnum.getStatus();
        this.message = httpStateEnum.getMessage();
    }

    public HttpRespond(HttpStateEnum httpStateEnum, T data, String accessToken) {
        this(httpStateEnum, accessToken);
        this.data = data;
        this.accessToken = accessToken;
    }

    public HttpRespond(HttpStateEnum httpStateEnum, String accessToken) {
        this(httpStateEnum);
        this.accessToken = accessToken;
    }

    public static HttpRespond unknownException() {
        return new HttpRespond(HttpStateEnum.UNKNOWN_EXCEPTION);
    }

    public static HttpRespond fileFormatException() {
        return new HttpRespond(HttpStateEnum.FILE_FORMAT_ERROR);

    }

    public void setAuthMessage(AuthMessage authMessage) {
        this.status = authMessage.getStatus();
        this.message = authMessage.getMessage();
        this.accessToken = authMessage.getAccessToken();
    }

    public void setHttpState(HttpStateEnum httpState) {
        this.status = httpState.getStatus();
        this.message = httpState.getMessage();
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "HttpRespond{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
