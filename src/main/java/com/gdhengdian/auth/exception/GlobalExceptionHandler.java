package com.gdhengdian.auth.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gdhengdian.auth.common.AuthMessage;
import com.gdhengdian.auth.common.HttpStateEnum;

/**
 * @author junmov guoyancheng@junmov.cn
 * @date 2018-10-11
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(AuthException.class)
    public AuthMessage handleAuthException(AuthException e) {
        HttpStateEnum httpState = e.getHttpState();	
        LOGGER.error("授权中点发生异常，异常原因是:{}", httpState.getMessage());
        e.printStackTrace();
        return new AuthMessage(e.getHttpState(), null);
    }

}
