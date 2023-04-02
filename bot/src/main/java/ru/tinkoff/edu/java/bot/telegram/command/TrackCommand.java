package ru.tinkoff.edu.java.bot.telegram.command;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Slf4j
public class TrackCommand extends AbstractCommand {

    public TrackCommand() {
        this("track", "начать отслеживание ссылки");
    }

    public TrackCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        String userName = user.getUserName();
        log.info(String.format("Пользователь %s. Команда %s", userName, this.getCommandIdentifier()));
        try {
            checkUrlIsOne(arguments);
            validateUrl(arguments[0]);
        } catch (IllegalArgumentException e) {
            sendAnswer(absSender, chat.getId(), e.getMessage());
            return;
        }
        printCommand(absSender, chat);
    }
}