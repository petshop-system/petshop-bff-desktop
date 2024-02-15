package com.petshopbffdesktop.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshopbffdesktop.exception.InternalServerErrorException;
import com.petshopbffdesktop.exception.NotFoundException;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@SuppressWarnings(value = {"raw", "unused", "unchecked"})
public class GenericService<T, R> implements IGenericService<T, R> {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public static String NOT_FOUND_EXCEPTION = "not found";

    public static String INTERNAL_SERVER_ERROR_EXCEPTION = "internal server error";

    private final ObjectMapper objectMapper;

    private final WebClient defaultWebClient;

    private final Class<R> type;

    public GenericService(WebClient defaultWebClient, Class<R> type, ObjectMapper objectMapper) {
        this.defaultWebClient = defaultWebClient;
        this.type = type;
        this.objectMapper = objectMapper;
    }

    private LinkedMultiValueMap getHeaders(Map<String, String> headers) {
        LinkedMultiValueMap multiValueMap = new LinkedMultiValueMap();
        if (!ObjectUtils.isEmpty(headers))
            headers.forEach(multiValueMap::add);
        return multiValueMap;
    }

    @Override
    public R get(String path, Map<String, String> headers) throws NotFoundException, InternalServerErrorException {

        Mono<R> resp = defaultWebClient.get()
                .uri(path)
                .headers(httpHeaders -> httpHeaders.addAll(this.getHeaders(headers)))
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    logger.error("error to execute get request for class {}, path {}", this.type, path);
                    return Mono.error(new InternalServerErrorException(GenericService.INTERNAL_SERVER_ERROR_EXCEPTION));
                })
                .onStatus(statusCode -> statusCode.value() == HttpStatus.NOT_FOUND.value(), response -> {
                    logger.warn("warning to execute get request for class {}, path {}", this.type, path);
                    return Mono.error(new NotFoundException(GenericService.NOT_FOUND_EXCEPTION));
                })
                .bodyToMono(type);

        return resp.block();
    }

    @Override
    public R post(String path, Map<String, String> headers, T t) throws NotFoundException, InternalServerErrorException {
        return defaultWebClient.post()
                .uri(path)
                .body(Mono.just(t), type)
                .headers(httpHeaders -> httpHeaders.addAll(this.getHeaders(headers)))
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    logger.error("error to execute post request for class {}, path {}", this.type, path);
                    return Mono.error(new InternalServerErrorException(GenericService.INTERNAL_SERVER_ERROR_EXCEPTION));
                })
                .onStatus(statusCode -> statusCode.value() == HttpStatus.NOT_FOUND.value(), response -> {
                    logger.warn("warning to execute post request for class {}, path {}", this.type, path);
                    return Mono.error(new NotFoundException(GenericService.NOT_FOUND_EXCEPTION));
                })
                .bodyToMono(type).block();
    }

    @Override
    public R put(String path, Map<String, String> headers, T t) throws NotFoundException, InternalServerErrorException {
        return defaultWebClient.put()
                .uri(path)
                .body(Mono.just(t), type)
                .headers(httpHeaders -> httpHeaders.addAll(this.getHeaders(headers)))
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    logger.error("error to execute put request for class {}, path {}", this.type, path);
                    return Mono.error(new InternalServerErrorException(GenericService.INTERNAL_SERVER_ERROR_EXCEPTION));
                })
                .onStatus(statusCode -> statusCode.value() == HttpStatus.NOT_FOUND.value(), response -> {
                    logger.warn("warning to execute put request for class {}, path {}", this.type, path);
                    return Mono.error(new NotFoundException(GenericService.NOT_FOUND_EXCEPTION));
                })
                .bodyToMono(type).block();
    }

    @Override
    public R delete(String path, Map<String, String> headers) throws NotFoundException, InternalServerErrorException {
        return defaultWebClient.delete()
                .uri(path)
                .headers(httpHeaders -> httpHeaders.addAll(this.getHeaders(headers)))
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    logger.error("error to execute delete request for class {}, path {}", this.type, path);
                    return Mono.error(new InternalServerErrorException(GenericService.INTERNAL_SERVER_ERROR_EXCEPTION));
                })
                .onStatus(statusCode -> statusCode.value() == HttpStatus.NOT_FOUND.value(), response -> {
                    logger.warn("warning to execute delete request for class {}, path {}", this.type, path);
                    return Mono.error(new NotFoundException(GenericService.NOT_FOUND_EXCEPTION));
                })
                .bodyToMono(type).block();
    }

}
