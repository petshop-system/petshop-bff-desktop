package com.petshopbffdesktop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ServiceConfiguration {

    @Bean
    public IServiceService serviceService(WebClient defaultWebClient, ObjectMapper objectMapper){
        return new ServiceService(defaultWebClient, objectMapper);
    }

}
