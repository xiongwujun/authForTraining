package com.gdhengdian.auth.service;

import com.gdhengdian.auth.common.AuthMessage;
import com.gdhengdian.auth.model.Token;

import java.util.List;

/**
 * 权限服务
 *
 * @author junmov guoyancheng@junmov.cn
 * @date 2018-10-10
 */
public interface AuthService {

    /**
     * 登录业务接口
     *
     * @param username 账号
     * @param password 密码
     * @return 令牌
     */
    AuthMessage login(String username, String password);

    /**
     * 登出业务接口
     *
     * @param accessToken 访问令牌
     * @return 处理结果
     */
    AuthMessage logout(String accessToken);

    /**
     * 授权业务接口
     *
     * @param accessToken 访问令牌
     * @param authCode    权限
     * @return 令牌
     */
    AuthMessage accredit(String accessToken, Integer authCode);

    /**
     * 重置密码业务接口
     *
     * @param accessToken 访问令牌
     * @param username    账号
     * @param password    密码
     * @return 处理结果
     */
    AuthMessage resetPassword(String accessToken, String username, String password);

    /**
     * 保存新令牌
     *
     * @param accessToken 访问令牌
     * @param tokenVoList 令牌列表
     * @return 是否成功
     */
    AuthMessage saveUserTokens(String accessToken, List<Token> tokenVoList);
}
