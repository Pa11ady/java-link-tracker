package ru.tinkoff.edu.java.scrapper.github;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GitClientConfiguration {
    private static final String BASE_URL = "https://api.github.com";

    @Bean
    public WebClient webClientGit() {

        return WebClient.builder()
                .baseUrl(BASE_URL)
                .build();
    }
}

