package com.petshopbffdesktop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    public IServiceService serviceService(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper){
        return new ServiceService(restTemplateBuilder, objectMapper);
    }

}
