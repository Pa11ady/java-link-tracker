package ru.tinkoff.edu.java.scrapper.configuration;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.ChatRepository;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.LinkRepository;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
@RequiredArgsConstructor
public class JdbcAccessConfiguration {
    private final JdbcTemplate jdbcTemplate;

    @Bean
    public ru.tinkoff.edu.java.scrapper.repository.ChatRepository chatRepository() {
        return new ChatRepository(jdbcTemplate);
    }

    @Bean
    public ru.tinkoff.edu.java.scrapper.repository.LinkRepository linkRepository() {
        return new LinkRepository(jdbcTemplate);
    }


}
