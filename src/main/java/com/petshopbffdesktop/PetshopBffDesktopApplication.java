package com.petshopbffdesktop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.client.RestTemplateBuilderConfigurer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PetshopBffDesktopApplication implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value(value = "${spring.config.activate.on-profile}")
    private String activeProfile;

    @Value(value = "${redis.host}")
    private String redisHost;

    @Value(value = "${redis.port}")
    private int redisPort;

    @Value(value = "${api-gateway.address}")
    private String apiGatewayAddress;


    @Value(value = "${api-gateway.timeout}")
    private int timeout;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(PetshopBffDesktopApplication.class);
        app.run();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.registerModule(new JavaTimeModule());
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
        return new JedisConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        return clientHttpRequestFactory;
    }

    @Bean
    public String apiGatewayAddress() {
        return apiGatewayAddress;
    }

    @Override
    public void run(String... args) {

        logger.info("#############################################################");

        logger.info("redisHost: {}", redisHost);
        logger.info("redisPort: {}", redisPort);
        logger.info("apiGatewayAddress: {}", this.apiGatewayAddress);
        logger.info("#############################################################");

    }

}
