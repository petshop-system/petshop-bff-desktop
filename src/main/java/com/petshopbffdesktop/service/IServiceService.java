package com.petshopbffdesktop.service;

import com.petshopbffdesktop.integration.IGenericService;

public interface IServiceService<ServiceDTO, ServiceResponse>
        extends IGenericService<ServiceDTO, ServiceResponse> {

    String URL_GET_BY_ID = "service/%s?active=true&contract=1";
}
