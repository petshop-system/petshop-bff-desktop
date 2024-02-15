package com.petshopbffdesktop.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Collection;

public record ServiceResponse (

        @JsonProperty(value = "message")
        String message,

        @JsonProperty(value = "date")
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        LocalDateTime date,

        @JsonProperty(value = "result")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        ServiceDTO serviceDTO,

        @JsonProperty(value = "results")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Collection<ServiceDTO> servicesDTO
){
}
