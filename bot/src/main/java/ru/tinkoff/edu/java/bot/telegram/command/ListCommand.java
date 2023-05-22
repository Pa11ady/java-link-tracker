package ru.tinkoff.edu.java.bot.telegram.command;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.tinkoff.edu.java.bot.webclient.ScrapperClient;
import ru.tinkoff.edu.java.bot.webclient.dto.LinkResponse;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ListCommand extends AbstractCommand {
    private final ScrapperClient scrapperClient;

    public ListCommand(ScrapperClient scrapperClient) {
        this("list", "показать список отслеживаемых ссылок",  scrapperClient);
    }

    public ListCommand(String identifier, String description, ScrapperClient scrapperClient) {
        super(identifier, description);
        this.scrapperClient = scrapperClient;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        String message = "Список ссылок пуст";
        printCommand(absSender, chat);
        String userName = user.getUserName();
        log.info(String.format("Пользователь %s. Команда %s", userName, this.getCommandIdentifier()));
        List<LinkResponse> links = scrapperClient.findAllLinks(chat.getId()).links();
        if (!links.isEmpty()) {
            message = links.stream()
                    .map(LinkResponse::url)
                    .map(URI::toString)
                    .collect(Collectors.joining("\n"));
        }
        sendAnswer(absSender, chat.getId(), message);
    }
}
