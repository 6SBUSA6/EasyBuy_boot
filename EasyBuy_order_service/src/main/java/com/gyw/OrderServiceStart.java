package com.gyw;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 高源蔚
 * @date 2021/12/20-21:41
 * @describe
 */
@SpringBootApplication
@EnableDubbo
public class OrderServiceStart {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceStart.class,args);
    }
}
