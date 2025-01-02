package com.xfy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.xfy.dao")
public class FeiqiSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeiqiSystemApplication.class, args);
    }

}
