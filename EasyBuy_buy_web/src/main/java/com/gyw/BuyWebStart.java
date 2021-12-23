package com.gyw;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 高源蔚
 * @date 2021/12/17-15:38
 * @describe
 */
@SpringBootApplication
@EnableDubbo
public class BuyWebStart {
    public static void main(String[] args) {
        System.out.println("前台启动成功了！");
        SpringApplication.run(BuyWebStart.class,args);

    }
}
