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

    private final ServiceUserCase serviceUserCase;

    public ServiceController(ServiceUserCase serviceUserCase) {
        this.serviceUserCase = serviceUserCase;
    }

    @GetMapping(path = {"/{id}", "/{id}/"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse getByID(@PathVariable(value = "id") Long id) throws NotFoundException, InternalServerErrorException, JsonProcessingException {
        String getByIDPath = String.format(ServiceUserCase.URL_GET_BY_ID, id);
        return (ServiceResponse) serviceUserCase.get(getByIDPath, null);
    }

    @GetMapping(path = {"", "/"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse getByFilters(@RequestParam(value = "active", defaultValue = "true") boolean active,
                                        @RequestParam(value = "contract", defaultValue = "1") Long contractID) throws NotFoundException, InternalServerErrorException, JsonProcessingException {
        String getByFiltersPath = String.format(ServiceUserCase.URL_GET_BY_FILTER, active, contractID);
        return (ServiceResponse) serviceUserCase.get(getByFiltersPath, null);
    }
}
