package com.gdhengdian.auth.conroller;

import com.gdhengdian.auth.common.ApiConstant;
import com.gdhengdian.auth.common.AuthMessage;
import com.gdhengdian.auth.model.LoginAo;
import com.gdhengdian.auth.model.ResetPasswordAo;
import com.gdhengdian.auth.model.Token;
import com.gdhengdian.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 授权控制中心
 *
 * @author junmov guoyancheng@junmov.cn
 * @date 2018-10-10
 */
@CrossOrigin
@RestController
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * 添加用户
     *
     * @param accessToken 执行该操作的用户
     * @param tokens      数据
     * @return 操作结果
     */
    @PostMapping(ApiConstant.TOKENS)
    public AuthMessage register(@Nullable @RequestHeader("access_token") String accessToken,
                                @Nullable @RequestBody List<Token> tokens) {
        if (accessToken == null || "null".equals(accessToken) || tokens == null || tokens.isEmpty()) {
            return AuthMessage.badRequest();
        }
        return authService.saveUserTokens(accessToken, tokens);
    }

    /**
     * 授权
     *
     * @param accessToken 权限申请者
     * @param authCode    权限代码
     * @return 授权结果
     */
    @PutMapping(ApiConstant.AUTH)
    public AuthMessage auth(@Nullable @RequestHeader("access_token") String accessToken,
                            @Nullable @RequestHeader("auth_code") Integer authCode) {
        if (accessToken == null || "null".equals(accessToken) || authCode == null) {
            return AuthMessage.badRequest();
        }
        System.out.println(accessToken + " " + authCode);
        return authService.accredit(accessToken, authCode);
    }

    /**
     * 登出
     *
     * @param accessToken 申请登出的用户
     * @return 处理结果
     */
    @DeleteMapping(ApiConstant.AUTH)
    public AuthMessage logout(@Nullable @RequestHeader("access_token") String accessToken) {
        if (accessToken == null || "null".equals(accessToken)) {
            return AuthMessage.badRequest();
        }
        return authService.logout(accessToken);
    }

    /**
     * 登录
     *
     * @param loginAo 账号和密码 就会被保存
     * @return 处理结果
     */
    @PostMapping(ApiConstant.AUTH)
    public AuthMessage login(@RequestBody LoginAo loginAo) {
        if (loginAo == null || loginAo.getPassword() == null || loginAo.getUsername() == null) {
            return AuthMessage.badRequest();
        }
        return authService.login(loginAo.getUsername(), loginAo.getPassword());
    }

    /**
     * 重置密码
     *
     * @param accessToken     操作执行者
     * @param resetPasswordAo 数据
     * @return 执行结果
     */
    @PutMapping(ApiConstant.TOKENS)
    public AuthMessage resetPassword(@Nullable @RequestHeader("access_token") String accessToken,
                                     @Nullable @RequestBody ResetPasswordAo resetPasswordAo) {

        if (accessToken == null || "null".equals(accessToken) || resetPasswordAo == null ||
                resetPasswordAo.getUsername() == null || resetPasswordAo.getPassword() == null) {
            return AuthMessage.badRequest();
        }
        String password = resetPasswordAo.getPassword();
        String username = resetPasswordAo.getUsername();
        return authService.resetPassword(accessToken, username, password);
    }

}
