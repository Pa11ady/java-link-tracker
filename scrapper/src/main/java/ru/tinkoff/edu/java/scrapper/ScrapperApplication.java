package ru.tinkoff.edu.java.scrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationConfig.class,  ApplicationProperties.class})
public class ScrapperApplication {
    public static void main(String[] args) {
        var context = SpringApplication.run(ScrapperApplication.class, args);
        ApplicationProperties properties = context.getBean(ApplicationProperties.class);
        System.out.println(properties.databaseAccessType());
    }
}
