package com.conor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>Description: </p>
 * <p>@Author conor  2021/6/3 </p>
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.conor"})
public class ConorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConorApplication.class, args);
    }
}
