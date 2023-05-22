package ru.tinkoff.edu.java.scrapper.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.botclient.BotClient;
import ru.tinkoff.edu.java.scrapper.dto.LinkUpdate;


@Slf4j
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false")
@Service
@RequiredArgsConstructor
public class HttpMessageServiceImpl implements MessageService {
    private final BotClient botClient;

    @Override
    public void send(LinkUpdate linkUpdate) {
        log.info("http");
        botClient.sendUpdate(linkUpdate);
    }
}
