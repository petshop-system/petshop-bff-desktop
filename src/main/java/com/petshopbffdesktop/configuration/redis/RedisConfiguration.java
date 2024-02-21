package com.petshopbffdesktop.configuration.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfiguration {

    @Bean
    @ConfigurationProperties("redis")
    RedisProperties redisProperties () {
        return new RedisProperties();
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {

        RedisProperties redisProperties = this.redisProperties();
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisProperties.getHost(),
                redisProperties.getPort());
        config.setDatabase(redisProperties.getDatabase());

        return new JedisConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

}
