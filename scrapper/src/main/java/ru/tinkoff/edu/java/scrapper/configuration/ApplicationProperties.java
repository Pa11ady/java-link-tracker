package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record ApplicationProperties(AccessType databaseAccessType, boolean useQueue, RabbitProperties rabbit) {
    public enum AccessType {
        JDBC, JPA, JOOQ
    }

    public record RabbitProperties(String exchange, String queue) {
    }
}
