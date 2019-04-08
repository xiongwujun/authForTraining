package com.gdhengdian.auth.manager;


import com.gdhengdian.auth.utils.TokenUtils;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author junmov guoyancheng@junmov.cn
 * @date 2018-10-10
 */
@Service
public class AuthManagerImpl implements AuthManager {

    @Override
    public String createAccessTokenId() {
        final char[] base = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                '\'', '\"', '.', ',', '/', '?', '<', '>', '|', '[', ']', '{', '}',
                '`', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '-', '=',
                '~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '+'};
        final Random random = new Random();
        final int accessTokenIdLength = 16;
        StringBuilder accessTokenId = new StringBuilder();
        int index;
        for (int i = 0; i < accessTokenIdLength; i++) {
            index = random.nextInt(91);
            accessTokenId.append(base[index]);
        }
        return accessTokenId.toString();
    }

    @Override
    public String createAccessTokenByAccessTokenId(String accessTokenId) {
        return TokenUtils.createAccessTokenByAccessTokenId(accessTokenId);
    }

    @Override
    public String getAccessTokenIdByAccessToken(String accessToken) {
        return TokenUtils.getAccessTokenIdByAccessToken(accessToken);
    }

    @Override
    public Boolean hasAuthToDoSomeThing(Integer userType, Integer authCode) {
        int auth = authCode / 100;
        return (auth & userType) != 0;
    }
}