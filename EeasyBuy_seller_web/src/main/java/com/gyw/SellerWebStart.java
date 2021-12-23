package com.gyw;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 高源蔚
 * @date 2021/12/14-17:17
 * @describe
 */
@SpringBootApplication
@EnableDubbo
public class SellerWebStart {
    public static void main(String[] args) {
        System.out.println("卖家后台启动");
        SpringApplication.run(SellerWebStart.class,args);
    }
}
