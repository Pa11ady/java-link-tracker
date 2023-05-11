package ru.tinkoff.edu.java.scrapper.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dto.LinkUpdate;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationProperties;

@Slf4j
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
@Service
@RequiredArgsConstructor
public class ScrapperQueueProducer implements MessageService{
    private final ApplicationProperties properties;
    private final RabbitTemplate rabbitTemplate;

    public void send(LinkUpdate linkUpdate) {
        log.info("Rabbit");
        rabbitTemplate.convertAndSend(properties.rabbit().exchange(), properties.rabbit().queue(), linkUpdate);
    }
}
