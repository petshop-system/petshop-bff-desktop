package com.petshopbffdesktop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshopbffdesktop.integration.GenericService;
import org.springframework.boot.web.client.RestTemplateBuilder;

public class ServiceService extends GenericService<ServiceDTO, ServiceResponse>
        implements IServiceService<ServiceDTO, ServiceResponse> {

    public ServiceService(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        super(restTemplateBuilder, ServiceResponse.class, objectMapper);
    }
}
