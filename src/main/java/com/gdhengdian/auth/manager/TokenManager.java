package com.gdhengdian.auth.manager;

import com.gdhengdian.auth.exception.AuthException;
import com.gdhengdian.auth.model.Token;

import java.util.List;

/**
 * @author junmov guoyancheng@junmov.cn
 * @since 2018-10-28
 */
public interface TokenManager {

    /**
     * 添加新的用户令牌
     *
     * @param tokens 用户令牌列表
     * @return 是否成功添加
     * @throws AuthException 插入重复数据异常
     */
    Boolean batchSaveTokens(List<Token> tokens) throws AuthException;

    /**
     * 根据账号密码获得用户令牌
     *
     * @param username 账号
     * @param password 密码
     * @return 令牌
     */
    Token getTokenByUsernameAndPassword(String username, String password);

    /**
     * 重置密码
     *
     * @param username 账号
     * @param password 新密码
     * @return 受影响的行数
     */
    Integer updatePassword(String username, String password);

    /**
     * 根据访问令牌查找用户令牌
     *
     * @param accessTokenId 访问令牌
     * @return 用户令牌
     */
    Token getTokenByAccessTokenId(String accessTokenId);

    /**
     * 根据id保存用户的访问令牌ID
     *
     * @param token 用户令牌Id和访问令牌ID
     * @return 用户令牌ID
     */
    Token saveAccessTokenIdByTokenId(Token token);


    /**
     * 根据id删除访问令牌ID
     *
     * @param token         用户令牌ID和访问令牌Id
     * @param accessTokenId 访问令牌ID
     * @return 删除结果
     */
    int removeAccessTokenIdByTokenId(Token token, String accessTokenId);

    /**
     * 根据账号获取用户的id和用户类型
     *
     * @param username 用户账号
     * @return id和用户类型
     */
    Token getUserTypeAndIdByUsername(String username);
    /**
     * 删除用户访问令牌的缓存
     * @param accessTokenId 用户访问令牌
     */
    void deleteAccessTokenIdCache(String accessTokenId);
}
