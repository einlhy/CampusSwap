package com.lhy.campusswap;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.lhy.campusswap.mapper")
public class CampusSwapApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusSwapApplication.class, args);
    }

}
