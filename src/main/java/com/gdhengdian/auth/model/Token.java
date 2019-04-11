package com.gdhengdian.auth.model;


import java.io.Serializable;

/**
 * 
 * 令牌实体类，具体存放的token信息
 * @author junmov guoyancheng@junmov.cn
 * @date 2018-10-10
 */
@SuppressWarnings("serial")
public class Token implements Serializable {
    /**
     * id 主键
     */
    private Integer id;
    /**
     * 账号   
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户类型
     */
    private Integer type;

    /**
     * 访问令牌ID
     */
    private String accessTokenId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAccessTokenId() {
        return accessTokenId;
    }

    public void setAccessTokenId(String accessTokenId) {
        this.accessTokenId = accessTokenId;
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                ", accessTokenId='" + accessTokenId + '\'' +
                '}';
    }
}
