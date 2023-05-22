package ru.tinkoff.edu.java.bot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApiClientConfiguration {
    @Bean
    public WebClient webClientScrapper(ApplicationConfig config) {
        return WebClient.builder()
                .baseUrl(config.baseUrl())
                .build();
    }
}
