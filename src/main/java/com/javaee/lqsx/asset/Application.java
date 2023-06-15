package com.javaee.lqsx.asset;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

//配置springboot的包扫描
@SpringBootApplication
@MapperScan("com.javaee.lqsx.asset.mapper")
public class Application {

    /**
     * 启动方法
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

}
