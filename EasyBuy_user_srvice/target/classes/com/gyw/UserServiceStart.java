package com.gyw;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 高源蔚
 * @date 2021/12/18-9:21
 * @describe
 */
@EnableDubbo
@SpringBootApplication
public class UserServiceStart {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceStart.class,args);
    }
}
