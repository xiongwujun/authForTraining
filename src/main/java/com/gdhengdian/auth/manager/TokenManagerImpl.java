package com.gdhengdian.auth.manager;

import com.gdhengdian.auth.exception.AuthException;
import com.gdhengdian.auth.mapper.TokenMapper;
import com.gdhengdian.auth.model.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author junmov guoyancheng@junmov.cn
 * @since 2018-10-28
 */
@Service
public class TokenManagerImpl implements TokenManager {

    private final static Logger LOGGER = LoggerFactory.getLogger(TokenManagerImpl.class);

    private final TokenMapper mapper;

    @Autowired
    public TokenManagerImpl(TokenMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Integer batchSaveTokens(List<Token> tokens) throws AuthException {
        return mapper.batchSaveUserToken(tokens);
    }

    @Override
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

    @Override
    @CachePut(key = "#token.accessTokenId", value = "token", condition = "#result!=null")
    public Integer saveAccessTokenIdByTokenId(Token token) {
        int i = mapper.updateAccessTokenIdByTokenId(token);
        if (i > 0) {
            LOGGER.info("成功为id为{}的用户创建access token", token.getId());
        }
        return token.getId();
    }

    @Override
    @CacheEvict(key = "#accessTokenId", value = "token", condition = "#	 > 0")
    public int removeAccessTokenIdByTokenId(Token token, String accessTokenId) {
        token.setAccessTokenId(accessTokenId);
        return mapper.updateAccessTokenIdByTokenId(token);
    }

    @Override
    public Token getUserTypeAndIdByUsername(String username) {
        return mapper.getUserTokenByUsername(username);
    }
}
