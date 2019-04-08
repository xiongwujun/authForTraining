package com.gdhengdian.auth.mapper;

import com.gdhengdian.auth.model.Token;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author junmov guoyancheng@junmov.cn
 * @since 2018-10-29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenMapperTest {

    @Autowired
    private TokenMapper mapper;

    @Test
    public void updatePassword() {
    }

    @Test
    public void getTokenByUsernameAndPassword() {
    }

    @Test
    public void batchSaveUserToken() {
    }

    @Test
    public void getUserTokenByAccessTokenId() {
        String accessTokenId = "Gb(m70WWbPp*w?iH";
        accessTokenId = "WOG*Kcj(%If>mL8i";
        Token token = mapper.getUserTokenByAccessTokenId(accessTokenId);
        System.out.println(token);
    }

    @Test
    public void updateAccessTokenIdByTokenId() {
//        Token token = new Token();
//        token.setId(1);
//        token.setAccessTokenId("");
//        mapper.updateAccessTokenIdByTokenId(token);
    }

    @Test
    public void getUserTokenByUsername() {
    }

}