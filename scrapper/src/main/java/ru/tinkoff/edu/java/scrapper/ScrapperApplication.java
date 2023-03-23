package ru.tinkoff.edu.java.scrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.scrapper.github.GitHubClient;
import ru.tinkoff.edu.java.scrapper.stackoverflow.StackOverflowClient;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class ScrapperApplication {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(ScrapperApplication.class, args);

        //тест задание 3
        StackOverflowClient stackOverflowClient = ctx.getBean(StackOverflowClient.class);
        GitHubClient gitHubClient = ctx.getBean(GitHubClient.class);

        System.out.println(gitHubClient.findRepository("sanyarnd", "tinkoff-java-course-2022"));

        System.out.println(stackOverflowClient.findQuestion("1642028"));

    }
}
