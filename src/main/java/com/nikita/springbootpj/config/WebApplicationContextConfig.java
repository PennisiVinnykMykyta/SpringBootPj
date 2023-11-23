package com.nikita.springbootpj.config;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebApplicationContextConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer()
    {
        return new WebMvcConfigurer()
        {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry)
            {
                registry
                        .addMapping("/**")
                        .allowedOrigins("http://localhost:8080,http://localhost:4200")  //http://localhost:8080,http://localhost:4200
                        .allowedMethods("*")
                        .allowedHeaders(HttpHeaders.CONTENT_TYPE, HttpHeaders.AUTHORIZATION, HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS);
            }
        };
    }
}
