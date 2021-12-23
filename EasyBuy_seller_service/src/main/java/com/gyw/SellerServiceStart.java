package com.gyw;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 高源蔚
 * @date 2021/12/14-14:57
 * @describe
 */
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
@EnableDubbo
public class SellerServiceStart {
    public static void main(String[] args) {
        SpringApplication.run(SellerServiceStart.class,args);
    }
}
