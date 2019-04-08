package com.gdhengdian.auth.common;

/**
 * @author junmov guoyancheng@junmov.cn
 * @date 2018-10-12
 */
public class UniqueKeyConflictException extends RuntimeException {
    private HttpStateEnum httpState;

    public UniqueKeyConflictException(HttpStateEnum httpState) {
        super();
        this.httpState = httpState;
    }

    public HttpStateEnum getHttpState() {
        return httpState;
    }
}
