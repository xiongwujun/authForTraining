package com.gdhengdian.auth.exception;

import com.gdhengdian.auth.common.HttpStateEnum;

/**
 * @author junmov guoyancheng@junmov.cn
 * @date 2018-10-11
 */
public class AuthException extends RuntimeException {
    private HttpStateEnum httpState;

    public AuthException(HttpStateEnum httpState) {
        super();
        this.httpState = httpState;
    }

    public HttpStateEnum getHttpState() {
        return httpState;
    }
}
