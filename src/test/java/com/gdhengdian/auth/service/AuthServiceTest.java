package com.gdhengdian.auth.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author junmov guoyancheng@junmov.cn
 * @since 2018-10-29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthServiceTest {

    @SuppressWarnings("unused")
	@Autowired
    private AuthService service;

    @Test
    public void login() {
//        String username = "teacher";
//        String password = "teacher";

//        String username = "username";
//        String password = "password";

//        String username = "student";
//        String password = "student3";
//        AuthMessage message = service.login(username, password);
//        System.out.println(message);
    }

    @Test
    public void logout() {
//        String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJQcj1qSX55PUNNaHFaKGhBIiwiaWF0IjoxNTQwNzgyNTU4LCJpc3MiOiJ3d3cuZ2RoZW5nZGlhbi5jb20iLCJleHAiOjE1NDA4MzU3NTh9.wsrBFaJSjOo0RkRxB5R3GmDEGtftximJxMRPfAqdRVk";
//        AuthMessage logout = service.logout(accessToken);
//        System.out.println(logout);
    }

    @Test
    public void accredit() {
//        String accessToken =
//                "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJvYSU2emx-J1Q2I1d6OCxKIiwiaWF0IjoxNTQwNzc5MDE1LCJpc3MiOiJ3d3cuZ2RoZW5nZGlhbi5jb20iLCJleHAiOjE1NDA4MzIyMTV9.XMh0_eu8gIlJALAObjGUDAs7H-zoIJEAFrGPowMS38U";
//        Integer authCode = AuthCodeConstant.CLASS_ADD;
//        AuthMessage accredit = service.accredit(accessToken, authCode);
//        System.out.println(accredit);
    }

    @Test
    public void resetPassword() {
//        String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJwVkpcIlAoPWsvI35sV1MsbyIsImlhdCI6MTU0MDc3OTkyNCwiaXNzIjoid3d3LmdkaGVuZ2RpYW4uY29tIiwiZXhwIjoxNTQwODMzMTI0fQ.LSguPblT4q5shKh5fEuyWXqdLUfGPwFdtyzBZ9f7xRc";
//        String username = "student";
//        String password = "student3";
//        AuthMessage message = service.resetPassword(accessToken, username, password);
//        System.out.println(message);
    }

    @Test
    public void saveUserTokens() {
//        List<Token> tokens = new ArrayList<>();
//        Token token = new Token();
//        token.setPassword("save_password");
//        token.setUsername("save_username");
//        token.setType(1);
//        tokens.add(token);
//        String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJ1d1ombGhhN3EsZko0XlY3IiwiaWF0IjoxNTQwNzgwNTAxLCJpc3MiOiJ3d3cuZ2RoZW5nZGlhbi5jb20iLCJleHAiOjE1NDA4MzM3MDF9.DjYSsQjVNjPtDp3Nxu_d8Y96_1ME__1fsPy3kZYYj1k";
//        AuthMessage message = service.saveUserTokens(accessToken, tokens);
//        System.out.println(message);
    }
}