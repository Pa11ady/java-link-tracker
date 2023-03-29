package ru.tinkoff.edu.java.scrapper.stackoverflow;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class StackOverflowClientConfiguration {
    private static final String BASE_URL = "https://api.stackexchange.com";

    @Bean
    public WebClient webCliStackOverflow() {

        return WebClient.builder()
                .baseUrl(BASE_URL)
                .build();
    }
}

