package com.petshopbffdesktop.service;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record ServiceDTO (

        @JsonProperty(value = "id")
        Long id,

        @JsonProperty(value = "name")
        String name,

        @JsonProperty(value = "description")
        String description,

        @JsonProperty(value = "price")
        BigDecimal price,

        @JsonProperty(value = "active")
        Boolean active
) {

}
