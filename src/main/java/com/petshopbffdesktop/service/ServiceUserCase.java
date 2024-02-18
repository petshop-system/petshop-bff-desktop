package com.petshopbffdesktop.service;

import com.petshopbffdesktop.integration.IGenericService;

public interface ServiceUserCase<T, R>
        extends IGenericService<T, R> {

    String URL_GET_BY_ID = "/service/%s?active=true&contract=1";

    String URL_CREATE = "/service";

    String URL_GET_BY_FILTER = "/service?active=%s&contract=%s";
}
