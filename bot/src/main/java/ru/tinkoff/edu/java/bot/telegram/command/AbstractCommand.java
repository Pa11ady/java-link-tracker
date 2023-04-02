package ru.tinkoff.edu.java.bot.telegram.command;

import lombok.SneakyThrows;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;


abstract class AbstractCommand extends BotCommand {
    AbstractCommand(String identifier, String description) {
        super(identifier, description);
    }


    void printCommand(AbsSender absSender, Chat chat) {
        String message = "Выполняется команда " + getCommandIdentifier();
        sendAnswer(absSender, chat.getId(), message);
    }

    @SneakyThrows
    void sendAnswer(AbsSender absSender, Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chatId.toString());
        message.setText(text);
        absSender.execute(message);
    }

    final void checkUrlIsOne(String[] url) {
        if (url.length != 1) {
            String message = "После команды \\" + getCommandIdentifier() + " только один Url в одну строку";
            throw new IllegalArgumentException(message);
        }
    }

    void validateUrl(String url) {
        try {
            new URL(url).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new IllegalArgumentException("Невалидная ссылка");
        }
    }
}
