package ru.tinkoff.edu.java.bot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.dto.LinkUpdate;

@Slf4j
@Service
@RabbitListener(queues = "${bot.rabbit.queue}")
@RequiredArgsConstructor
public class ScrapperQueueListener {
    private final BotService botService;

    @RabbitHandler
    public void listen(LinkUpdate update) {
        log.info("rabbit");
        log.info(update.toString());
        botService.notify(update);
    }
}
