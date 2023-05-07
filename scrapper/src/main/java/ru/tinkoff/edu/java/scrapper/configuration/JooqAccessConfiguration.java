package ru.tinkoff.edu.java.scrapper.configuration;


import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.tinkoff.edu.java.scrapper.repository.ChatRepository;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.LinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.jooq.JooqChatRepository;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
@RequiredArgsConstructor
public class JooqAccessConfiguration {
    private final DSLContext dslContext;
    private final JdbcTemplate jdbcTemplate;

    @Bean
    public ChatRepository chatRepository() {
        return new JooqChatRepository(dslContext);
    }

    @Bean
    public ru.tinkoff.edu.java.scrapper.repository.LinkRepository linkRepository() {
        return new LinkRepository(jdbcTemplate);
    }
}
