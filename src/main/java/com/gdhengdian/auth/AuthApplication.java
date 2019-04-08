package com.gdhengdian.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author junmov guoyancheng@junmov.cn
 * @date 2018-10-09
 */
@SpringBootApplication
@EnableCaching
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
