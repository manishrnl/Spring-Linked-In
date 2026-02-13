package com.example.post_service.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableJpaAuditing(auditorAwareRef = "getAuditorAwareImpl")//this line will enable Auditing
public class AppConfig {
    @Bean
    ModelMapper createModelMapper() {
        return new ModelMapper();
    }

}
