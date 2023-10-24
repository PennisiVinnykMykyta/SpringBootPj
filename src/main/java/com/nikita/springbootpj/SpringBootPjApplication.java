package com.nikita.springbootpj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication//(exclude = {DataSourceAutoConfiguration.class })
public class SpringBootPjApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootPjApplication.class, args);
    }

}
