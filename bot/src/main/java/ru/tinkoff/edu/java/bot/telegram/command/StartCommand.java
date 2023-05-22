package ru.tinkoff.edu.java.bot.telegram.command;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.tinkoff.edu.java.bot.webclient.ScrapperClient;

@Slf4j
public class StartCommand extends AbstractCommand {
    private final ScrapperClient scrapperClient;

    public StartCommand(ScrapperClient scrapperClient) {
        this("start", "зарегистрировать пользователя", scrapperClient);
    }

    public StartCommand(String identifier, String description, ScrapperClient scrapperClient) {
        super(identifier, description);
        this.scrapperClient = scrapperClient;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        String userName = user.getUserName();
        log.info(String.format("Пользователь %s. Команда %s", userName, this.getCommandIdentifier()));
        sendAnswer(absSender, chat.getId(), "Бот для отслеживания ссылок. Помощь /help");
        scrapperClient.regChat(chat.getId());
    }
}
