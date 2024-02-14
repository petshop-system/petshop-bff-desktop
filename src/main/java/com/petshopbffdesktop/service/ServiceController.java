package com.petshopbffdesktop.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petshopbffdesktop.exception.InternalServerErrorException;
import com.petshopbffdesktop.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings(value = {"raw", "unchecked", "inline"})
@RequestMapping(path = "/service", produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
public class ServiceController {

    private final String apiGatewayAddress;

    private final IServiceService serviceService;

    public ServiceController(IServiceService serviceService,
                             String apiGatewayAddress) {
        this.serviceService = serviceService;
        this.apiGatewayAddress = apiGatewayAddress;
    }

    @GetMapping(path = {"/{id}", "{id}/"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse getByID(@PathVariable(value = "id") Long id) throws NotFoundException, InternalServerErrorException, JsonProcessingException {

        String getByIDPath = String.format(IServiceService.URL_GET_BY_ID, id);
        String url = String.format("%s/%s", apiGatewayAddress, getByIDPath);
        ServiceResponse resp = (ServiceResponse) serviceService.get(url, null);

        return resp;
    }
}
