package com.gdhengdian.auth.model;

/**
 * 用于接收用户修改密码操作的数据
 *
 * @author junmov guoyancheng@junmov.cn
 * @since 2018-10-28
 */
public class ResetPasswordAo {


    private String username;

    private String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ResetPasswordAo{" +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
