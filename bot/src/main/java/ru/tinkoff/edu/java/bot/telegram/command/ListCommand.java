package ru.tinkoff.edu.java.bot.telegram.command;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Slf4j
public class ListCommand extends AbstractCommand {

    public ListCommand() {
        this("list", "показать список отслеживаемых ссылок");
    }

    public ListCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        printCommand(absSender, chat);
        String userName = user.getUserName();
        log.info(String.format("Пользователь %s. Команда %s", userName, this.getCommandIdentifier()));
        sendAnswer(absSender, chat.getId(), "Список ссылок пусть.");
    }
}