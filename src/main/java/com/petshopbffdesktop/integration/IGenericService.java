package com.petshopbffdesktop.integration;

import com.petshopbffdesktop.exception.InternalServerErrorException;
import com.petshopbffdesktop.exception.NotFoundException;

import java.util.Map;

public interface IGenericService<T, R> {

    R get (String path, Map<String, String> headers) throws NotFoundException, InternalServerErrorException;

    R post (String path, Map<String, String> headers, T t) throws NotFoundException, InternalServerErrorException;

    R put (String path, Map<String, String> headers, T t) throws NotFoundException, InternalServerErrorException;

    R delete (String path, Map<String, String> headers) throws NotFoundException, InternalServerErrorException;

}
