package com.petshopbffdesktop.integration;

import com.petshopbffdesktop.exception.InternalServerErrorException;
import com.petshopbffdesktop.exception.NotFoundException;

import java.util.Collection;
import java.util.Map;

public interface IGenericService<T, R> {

    R get (String url, Map<String, String> header) throws NotFoundException, InternalServerErrorException;

    R post (String url, Map<String, String> header, T t) throws NotFoundException, InternalServerErrorException;

}
