package com.nikita.springbootpj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public MapperConfig mapper() {
        return new MapperConfig();
    }
}
