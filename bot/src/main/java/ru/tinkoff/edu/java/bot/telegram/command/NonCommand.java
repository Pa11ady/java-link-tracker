package ru.tinkoff.edu.java.bot.telegram.command;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NonCommand {
    public String nonCommandExecute(Long chatId, String userName, String text) {
        log.info("Пользователь " + userName + " чат id " + chatId + " неизвестная команда " + text);
        return "Неизвестная команда. Помощь /help";
    }
}
