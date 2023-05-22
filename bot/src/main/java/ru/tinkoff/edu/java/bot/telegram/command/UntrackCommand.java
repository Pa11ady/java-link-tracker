package ru.tinkoff.edu.java.bot.telegram.command;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.tinkoff.edu.java.bot.webclient.ScrapperClient;

@Slf4j
public class UntrackCommand extends AbstractCommand {
    private final ScrapperClient scrapperClient;

    public UntrackCommand(ScrapperClient scrapperClient) {
        this("untrack", "прекратить отслеживание ссылки", scrapperClient);
    }

    public UntrackCommand(String identifier, String description, ScrapperClient scrapperClient) {
        super(identifier, description);
        this.scrapperClient = scrapperClient;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        String userName = user.getUserName();
        log.info(String.format("Пользователь %s. Команда %s", userName, this.getCommandIdentifier()));
        try {
            checkArraySizeIsOne(arguments);
            validateUrl(arguments[0]);
        } catch (IllegalArgumentException e) {
            sendAnswer(absSender, chat.getId(), e.getMessage());
            return;
        }
        printCommand(absSender, chat);
        scrapperClient.removeLink(chat.getId(), arguments[0]);
    }
}
