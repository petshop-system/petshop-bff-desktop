package com.petshopbffdesktop.configuration.redis;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class RedisProperties {

    private String host;

    private int port;

    private int database;

}
