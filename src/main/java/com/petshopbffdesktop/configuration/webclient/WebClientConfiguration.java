package com.petshopbffdesktop.configuration.webclient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfiguration {

    @Bean
    @ConfigurationProperties("webclient.default")
    public WebClientProperties defaultWebClientProperties () {
        return new WebClientProperties();
    }

    @Bean
    public WebClient defaultWebClient(WebClientProperties defaultWebClientProperties) {
        return WebClient.builder()
                .baseUrl(defaultWebClientProperties.getApiGatewayAddress())
//                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8080"))
                .clientConnector(new ReactorClientHttpConnector(this.defaultHttpClient(defaultWebClientProperties)))
                .build();
    }

    private HttpClient defaultHttpClient(WebClientProperties defaultWebClientProperties) {
        return HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, defaultWebClientProperties.getApiGatewayTimeout())
                .responseTimeout(Duration.ofMillis(defaultWebClientProperties.getApiGatewayTimeout()))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(defaultWebClientProperties.getApiGatewayTimeout(), TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(defaultWebClientProperties.getApiGatewayTimeout(), TimeUnit.MILLISECONDS)));
    }

}
