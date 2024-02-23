package com.petshopbffdesktop.configuration.webclient;

import lombok.Data;

@Data
public class WebClientProperties {

    private String apiGatewayAddress;

    private int apiGatewayTimeout;

}
