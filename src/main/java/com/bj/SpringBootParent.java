package com.bj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author ：Yang Li
 * @description：
 * @modified By：
 * @version:
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableSwagger2
@ServletComponentScan

public class SpringBootParent {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootParent.class, args);
    }
}

