package com.gdhengdian.auth.model;

/**
 * @author junmov guoyancheng@junmov.cn
 * @since 2018-10-29
 */
public class LoginAo {
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
        return "LoginAo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
