package ru.tinkoff.edu.java.bot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.telegram.TelegramBot;
import ru.tinkoff.edu.java.bot.webclient.ScrapperClient;

@Configuration
public class TelegramBotConfiguration {
    @Bean
    public TelegramBot telegramBot(ApplicationConfig config, ScrapperClient scrapperClient) {
        return new TelegramBot(config.name(), config.token(), scrapperClient);
    }
}
