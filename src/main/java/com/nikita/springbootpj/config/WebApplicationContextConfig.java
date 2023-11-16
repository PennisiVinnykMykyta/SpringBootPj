package com.nikita.springbootpj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration //cosi che spring sappia che questa è la classe di configurazione
public class WebApplicationContextConfig implements WebMvcConfigurer { //gestisce le configurazioni dell'applicazione

    @Bean
    public WebMvcConfigurer corsConfigurer()
    {
        return new WebMvcConfigurer()
        {
            @Override
            public void addCorsMappings(CorsRegistry registry)
            {
                registry
                        .addMapping("/**")
                        .allowedOrigins("http://localhost:8080,http://localhost:4200")  //http://localhost:8080,http://localhost:4200
                        .allowedMethods("POST","GET","PUT","DELETE");
            }
        };
    }
}
