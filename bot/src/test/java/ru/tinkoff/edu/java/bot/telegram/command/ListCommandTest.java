package ru.tinkoff.edu.java.bot.telegram.command;

import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ListCommandTest {

    @Mock
    AbsSender absSender;

    @Mock
    User user;

    @Mock
    Chat chat;

    @Test
    void execute_ShouldBeSpecialMessage() {
        //given
        StubListCommand listCommand = new StubListCommand();
        String[] arguments = null;
        Mockito.when(chat.getId()).thenReturn(1L);
        Mockito.when(user.getUserName()).thenReturn("User1");

        //when
        listCommand.execute(absSender, user, chat, arguments);

        //then
        assertEquals("Список ссылок пуст", listCommand.getAnswer());
    }
}

/**
 * Класс заглушка, который делает всё, что тестируемый родитель, но позволяет узнать ответ родителя
 */

@Getter
@Setter
class StubListCommand extends ListCommand {
    String answer = "";

    @Override
    void sendAnswer(AbsSender absSender, Long chatId, String text) {
        super.sendAnswer(absSender, chatId, text);
        answer = text;
    }
}