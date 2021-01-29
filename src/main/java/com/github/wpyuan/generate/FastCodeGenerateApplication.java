package com.github.wpyuan.generate;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.github.wpyuan.generate.mapper.*", "com.github.wpyuan.generate.infra.mapper.*"})
public class FastCodeGenerateApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastCodeGenerateApplication.class, args);
    }

}
