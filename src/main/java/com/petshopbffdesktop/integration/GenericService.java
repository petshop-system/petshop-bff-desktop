package com.petshopbffdesktop.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshopbffdesktop.exception.InternalServerErrorException;
import com.petshopbffdesktop.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Map;

@SuppressWarnings(value = {"unused", "unchecked"})
public class GenericService<T, R> implements IGenericService<T, R> {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public static String NOT_FOUND_EXCEPTION = "not found";

    public static String INTERNAL_SERVER_ERROR_EXCEPTION = "internal server error";

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    private final Class<R> type;

    public GenericService(RestTemplateBuilder restTemplateBuilder, Class<R> type, ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder.build();
        this.type = type;
        this.objectMapper = objectMapper;
    }

    @Override
    public R get(String url, Map<String, String> header) throws NotFoundException, InternalServerErrorException {

        try {

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new NotFoundException(GenericService.NOT_FOUND_EXCEPTION);
            }

            if (response.getStatusCode().is2xxSuccessful()) {
                return objectMapper.readValue(response.getBody(), type);
            }

        } catch (RestClientException | JsonProcessingException e) {
            logger.info("error to execute get request for class {}, error {}",
                    this.type, e.getMessage());
        }

        throw new InternalServerErrorException(GenericService.INTERNAL_SERVER_ERROR_EXCEPTION);
    }

    @Override
    public R post(String url, Map<String, String> header, T t) throws NotFoundException, InternalServerErrorException {

        try {

            ResponseEntity<String> response = restTemplate.postForEntity(url, t, String.class);
            if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new NotFoundException(GenericService.NOT_FOUND_EXCEPTION);
            }

            if (response.getStatusCode().is2xxSuccessful())
                return objectMapper.readValue(response.getBody(), type);

        } catch (RestClientException | JsonProcessingException e) {
            logger.info("error to execute post request for class {}, error {}",
                    this.type, e.getMessage());
        }

        throw new InternalServerErrorException(GenericService.INTERNAL_SERVER_ERROR_EXCEPTION);
    }

}
