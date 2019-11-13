package com.dajian.vhrserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.dajian.vhrserver.mapper")
@EnableCaching
public class VhrServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(VhrServerApplication.class, args);
    }
}
