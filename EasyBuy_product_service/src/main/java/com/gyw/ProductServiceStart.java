package com.gyw;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 高源蔚
 * @date 2021/12/15-10:01
 * @describe
 */
@SpringBootApplication
@EnableDubbo
public class ProductServiceStart {
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceStart.class,args);
    }
}
