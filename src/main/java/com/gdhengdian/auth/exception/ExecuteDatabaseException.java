package com.gdhengdian.auth.exception;

import com.gdhengdian.auth.common.HttpStateEnum;

@SuppressWarnings("serial")
public class ExecuteDatabaseException extends RuntimeException{
	private HttpStateEnum httpState;

    public ExecuteDatabaseException(HttpStateEnum httpState) {
        super();
        this.httpState = httpState;
    }

    public HttpStateEnum getHttpState() {
        return httpState;
    }
}
