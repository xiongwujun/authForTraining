package com.gdhengdian.auth.service;

import com.gdhengdian.auth.common.AuthCodeConstant;
import com.gdhengdian.auth.common.AuthMessage;
import com.gdhengdian.auth.common.HttpStateEnum;
import com.gdhengdian.auth.common.RoleConstant;
import com.gdhengdian.auth.exception.AuthException;
import com.gdhengdian.auth.manager.AuthManager;
import com.gdhengdian.auth.manager.TokenManager;
import com.gdhengdian.auth.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author junmov guoyancheng@junmov.cn
 * @date 2018-10-10
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final static Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final AuthManager authManager;
    private final TokenManager tokenManager;

    @Autowired
    public AuthServiceImpl(AuthManager authManager, TokenManager tokenManager) {
        this.authManager = authManager;
        this.tokenManager = tokenManager;
    }


    @Override
    public AuthMessage login(String username, String password) {
        // 1. 校验输入参数是否为空
        boolean hasNull = this.hasNull(username, password);
        if (hasNull) {
            return AuthMessage.badRequest();
        }
        // 2. 验证是否正确并讲结果放入缓存，这是第一次登陆，还没有token
        Token userTypeAndId = tokenManager.getTokenByUsernameAndPassword(username, password);
        if (userTypeAndId == null) {
            return new AuthMessage(HttpStateEnum.USERNAME_OR_PASSWORD_ERROR, null);
        }	
        // 4. 创建新的访问令牌并放入缓存
        String accessTokenId = authManager.createAccessTokenId();
        String accessToken = authManager.createAccessTokenByAccessTokenId(accessTokenId);
        userTypeAndId.setAccessTokenId(accessTokenId);
        Integer success = tokenManager.saveAccessTokenIdByTokenId(userTypeAndId);
        if (success == 0) {
            return new AuthMessage(HttpStateEnum.UNKNOWN_EXCEPTION, null);
        }
        LOGGER.info("id为{}类型为{}的用户登录成功", userTypeAndId.getId(), userTypeAndId.getType());
        // 5. 返回用户令牌，和成功状态码
        return AuthMessage.success(accessToken);
    }

    @Override
    public AuthMessage logout(String accessToken) {
        // 1. 校验输入参数是否为空
        boolean hasNull = this.hasNull(accessToken);
        if (hasNull) {
            return AuthMessage.badRequest();
        }
        // 2. 验证访问令牌并获得访问令牌id
        String accessTokenId = authManager.getAccessTokenIdByAccessToken(accessToken);
        // 3. 从缓存中获取用户令牌ID
        Token token = tokenManager.getTokenByAccessTokenId(accessTokenId);
        // 4. 从缓存中删除数据
        int i = tokenManager.removeAccessTokenIdByTokenId(token, accessTokenId);
        if (i > 0) {
            LOGGER.info("id为{}的用户登出成功", token.getId());
        }
        return AuthMessage.success(null);
    }

    @Override
    public AuthMessage accredit(String accessToken, Integer authCode) {
        // 1. 校验输入参数是否为空
        boolean hasNull = this.hasNull(accessToken, authCode);
        if (hasNull) {
            return AuthMessage.badRequest();
        }
        // 2. 判断权限
        Boolean hasAuth = this.hasAuthToDoSomeThing(accessToken, authCode);
        //有权限
        if (hasAuth) {
            return AuthMessage.success(null);
        } else {
            return new AuthMessage(HttpStateEnum.UNAUTHORIZED, null);
        }
    }
    //不一定是重置自己的密码，可以改密码
    @Override
    public AuthMessage resetPassword(String accessToken, String username, String password) {
        // 1. 校验输入参数是否为空
        boolean hasNull = this.hasNull(accessToken, username, password);
        if (hasNull) {
            return AuthMessage.badRequest();
        }
        // 2. 判断用户是否有权限并是否执行重置密码
        // 2.1 获取操作执行者的用户类型、id
        //如果这里找不到会报错 这里accessTokenId可能为空
        String accessTokenId = authManager.getAccessTokenIdByAccessToken(accessToken);
        
        if (accessTokenId == null) {
            return AuthMessage.badRequest();
        }
        
        Token userTypeAndId = tokenManager.getTokenByAccessTokenId(accessTokenId);
        
        // 2.2 获取被执行者的用户类型、id
        Token targetUserType = tokenManager.getUserTypeAndIdByUsername(username);
        
        // 2.3 判断能否执行
        int run = 0;
        //看是否为空
        hasNull = this.hasNull(userTypeAndId, targetUserType);
        
        
        if (hasNull) {
            return new AuthMessage(HttpStateEnum.NOT_FOUND, null);
        }
        //如果是管理员
        if (RoleConstant.ADMIN == userTypeAndId.getType()) {
            run = 1;
        } else {
        	//都是同一类型
            if (userTypeAndId.getId().intValue() == targetUserType.getId().intValue()) {
                run = 2;
            }
        }
        // 权限判断完毕,更新密码
        if (run == 0) {
            return new AuthMessage(HttpStateEnum.UNAUTHORIZED, null);
        }
        Integer success = tokenManager.updatePassword(username, password);
        if (success == 0) {
            return new AuthMessage(HttpStateEnum.UNKNOWN_EXCEPTION, null);
        }
        LOGGER.info("id为{}的用户给id为{}的用户重置密码成功", userTypeAndId.getId(), targetUserType.getId());

        //
        //      重点警告 可能会出现没有找到键(accessTokenId)的异常,换句话说，这句日志永远不会打印!!!!!!
        //
        if (targetUserType.getAccessTokenId() != null) {
            int i = tokenManager.removeAccessTokenIdByTokenId(targetUserType, targetUserType.getAccessTokenId());
            if (i > 0) {
                LOGGER.info("id为{}的用户已被强制下线", targetUserType.getId());
            }
        }
        return AuthMessage.success(null);
    }


    @Override
    @Transactional(rollbackFor = AuthException.class)
    public AuthMessage saveUserTokens(String accessToken, List<Token> tokenVoList) {
        // 1. 校验输入参数是否为空
        boolean hasNull = this.hasNull(accessToken, tokenVoList);
        if (hasNull || tokenVoList.isEmpty()) {
            return AuthMessage.badRequest();
        }
        //保存用户的权限代码
        Integer authCode = AuthCodeConstant.SAVE_USER_TOKEN;
        Boolean hasAuth = this.hasAuthToDoSomeThing(accessToken, authCode);
        if (hasAuth) {
            try {
                Integer count = tokenManager.batchSaveTokens(tokenVoList);
                LOGGER.info("添加了{}个用户", count);
            } catch (Exception e) {
                throw new AuthException(HttpStateEnum.REPEAT_DATA);
            }
            return AuthMessage.success(null);
        } else {
            return new AuthMessage(HttpStateEnum.UNAUTHORIZED, null);
        }
    }

    /**
     * 判断是否有权限
     * @param accessToken
     * @param authCode
     * @return
     */
    private Boolean hasAuthToDoSomeThing(String accessToken, Integer authCode) {
        String accessTokenId = authManager.getAccessTokenIdByAccessToken(accessToken);
        //通过tokenID拿到了Token实体类
        //TokenId
        Token userTypeAndId = tokenManager.getTokenByAccessTokenId(accessTokenId);
        if (userTypeAndId == null) {
            return false;
        }
        LOGGER.info("id为{}想要执行代号为{}的操作", userTypeAndId.getId(), authCode);
        return authManager.hasAuthToDoSomeThing(userTypeAndId.getType(), authCode);
    }

    /**
     * 判断是否有空值
     *
     * @param args 参数列表
     * @return 是否有空值
     */
    private boolean hasNull(Object... args) {
        for (Object obj : args) {
            if (Objects.isNull(obj)) {
                return true;
            }
        }
        return false;
    }
}
