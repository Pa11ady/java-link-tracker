package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.tinkoff.edu.java.scrapper.botclient.BotClient;
import ru.tinkoff.edu.java.scrapper.scheduler.Scheduler;

import java.time.Duration;

@Configuration
@EnableScheduling
@ConfigurationProperties(prefix = "app.scheduler")
public class ApplicationConfig {

    private Duration interval;

    public Duration getInterval() {
        return interval;
    }

    public void setInterval(Duration interval) {
        this.interval = interval;
    }

    @Bean
    public Scheduler scheduler() {
        return new Scheduler(interval);
    }

    @Bean
    public BotClient botClient() {
        return BotClient.create();
    }
}