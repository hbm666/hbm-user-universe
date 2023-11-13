package com.hbm.hbmuseruniverse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lenovo
 */
@SpringBootApplication
@MapperScan("com.hbm.hbmuseruniverse.mapper")
public class HbmUserUniverseApplication {
    public static void main(String[] args) {
        SpringApplication.run(HbmUserUniverseApplication.class, args);
    }
}
