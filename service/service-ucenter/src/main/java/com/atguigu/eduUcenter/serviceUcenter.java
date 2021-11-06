package com.atguigu.eduUcenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author
 */
@SpringBootApplication
@ComponentScan("com.atguigu")
@MapperScan("com.atguigu.eduUcenter.mapper")
@EnableDiscoveryClient
public class serviceUcenter {
    public static void main(String[] args) {
        SpringApplication.run(serviceUcenter.class, args);
    }
}
