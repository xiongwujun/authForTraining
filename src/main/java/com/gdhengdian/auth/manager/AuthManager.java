package com.gdhengdian.auth.manager;



/**
 * 授权接口
 * <p>
 * token的键说明：
 * 1. ISSUER : 签发者
 * 2. SUBJECT: 面向的用户
 * 3. AUDIENCE: 接收方
 * 4. EXPIRATION: 过期时间
 * 5. Not Before: 生效时间
 * 6. ISSUED AT:签发时间
 * 7. JWT_ID:　jwt的唯一身份标识，主要用来作为一次性token,从而回避重放工具
 *
 * @author junmov guoyancheng@junmov.cn
 * @date 2018-10-10
 */
public interface AuthManager {

    /**
     * 创建访问令牌的id
     *
     * @return 访问令牌的id
     */
    String createAccessTokenId();

    /**
     * 根据访问令牌ID创建一个访问令牌
     *
     * @param accessTokenId 访问令牌id
     * @return 访问令牌
     */
    String createAccessTokenByAccessTokenId(String accessTokenId);

    /**
     * 验证和解析访问令牌并获得访问令牌id
     *
     * @param accessToken 访问令牌
     * @return 用户id
     */
    String getAccessTokenIdByAccessToken(String accessToken);

    /**
     * 是否有权限去做某事
     *
     * @param userType 用户类型
     * @param authCode 权限代码
     * @return 是或否
     */
    Boolean hasAuthToDoSomeThing(Integer userType, Integer authCode);


}
