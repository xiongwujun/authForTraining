package com.gdhengdian.auth.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdhengdian.auth.common.HttpStateEnum;
import com.gdhengdian.auth.exception.AuthException;
import com.gdhengdian.auth.exception.ExecuteDatabaseException;
import com.gdhengdian.auth.mapper.TokenMapper;
import com.gdhengdian.auth.model.Token;

/**
 * @author junmov guoyancheng@junmov.cn
 * @since 2018-10-28
 */
@Service
@Transactional
public class TokenManagerImpl implements TokenManager {

    private final TokenMapper mapper;

    @Autowired
    public TokenManagerImpl(TokenMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Boolean batchSaveTokens(List<Token> tokens) throws AuthException {
       int number = mapper.batchSaveUserToken(tokens);
       if(number!=tokens.size()) {
    	   throw new ExecuteDatabaseException(HttpStateEnum.UNKNOWN_EXCEPTION);
       }
       return  true;
    }

    @Override
    //缓存的key：主键id  缓存的value：查询到的用户对象   
    @CachePut(key = "#result.id", value = "user", condition = "#result!=null")
    public Token getTokenByUsernameAndPassword(String username, String password) {
        return mapper.getTokenByUsernameAndPassword(username, password);
    }

    @Override
    public Integer updatePassword(String username, String password) {
        return mapper.updatePassword(username, password);
    }

    @Override
    @Cacheable(key = "#accessTokenId", value = "token", condition = "#result!=null")
    public Token getTokenByAccessTokenId(String accessTokenId) {
        return mapper.getUserTokenByAccessTokenId(accessTokenId);
    }
    //更新缓存
    @Override
    @CachePut(key = "#token.accessTokenId", value = "token", condition = "#result!=null")
    public Token saveAccessTokenIdByTokenId(Token token) {
        return token;
    }

    @Override
    @CacheEvict(key = "#accessTokenId", value = "token", condition = "#result>0")
    public int removeAccessTokenIdByTokenId(Token token, String accessTokenId) {
        token.setAccessTokenId(null);
        return mapper.updateAccessTokenIdByTokenId(token);
    }

    @Override
    public Token getUserTypeAndIdByUsername(String username) {
        return mapper.getUserTokenByUsername(username);
    }
}
