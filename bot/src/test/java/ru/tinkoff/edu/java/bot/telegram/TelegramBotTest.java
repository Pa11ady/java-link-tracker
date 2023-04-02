package ru.tinkoff.edu.java.bot.telegram;

import org.junit.jupiter.api.Test;
import ru.tinkoff.edu.java.bot.telegram.command.NonCommand;

import static org.junit.jupiter.api.Assertions.*;

class TelegramBotTest {

    @Test
    void test_UnknownCommand() {
        //given
        Long chatId = 1L;
        String userName = "User1";
        String text = "text1";
        NonCommand command = new NonCommand();

        //when
        String result = command.nonCommandExecute(chatId, userName, text);

        //then
        assertEquals("Неизвестная команда. Помощь /help",  result);
    }
}