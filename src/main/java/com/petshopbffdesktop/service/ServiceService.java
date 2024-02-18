package com.petshopbffdesktop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshopbffdesktop.integration.GenericService;
import org.springframework.web.reactive.function.client.WebClient;

public class ServiceService extends GenericService<ServiceDTO, ServiceResponse>
        implements ServiceUserCase<ServiceDTO, ServiceResponse> {

    public ServiceService(WebClient defaultWebClient, ObjectMapper objectMapper) {
        super(defaultWebClient, ServiceResponse.class, objectMapper);
    }
}
