package ru.tinkoff.edu.java.bot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.dto.LinkUpdate;
import ru.tinkoff.edu.java.bot.service.BotService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BotController {
    private final BotService botService;

    @PostMapping("/updates")
    public void sendUpdate(@RequestBody LinkUpdate request) {
        log.info(request.toString());
        botService.notify(request);
    }
}
