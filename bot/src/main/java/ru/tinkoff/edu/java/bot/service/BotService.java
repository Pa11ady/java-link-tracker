package ru.tinkoff.edu.java.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.dto.LinkUpdate;
import ru.tinkoff.edu.java.bot.telegram.TelegramBot;

@Service
@RequiredArgsConstructor
public class BotService {
    private final TelegramBot bot;

    public void notify(LinkUpdate update) {
        for (long id : update.tgChatIds()) {
            String message = String.format("Ссылка %s обновилась.\n%s", update.url(), update.description());
            bot.sendMessage(id, message);
        }
    }
}
