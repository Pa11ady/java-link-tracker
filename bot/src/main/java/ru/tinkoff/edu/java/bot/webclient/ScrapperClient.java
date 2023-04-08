package ru.tinkoff.edu.java.bot.webclient;

import ru.tinkoff.edu.java.bot.webclient.dto.LinkResponse;
import ru.tinkoff.edu.java.bot.webclient.dto.ListLinkResponse;

public interface ScrapperClient {
    LinkResponse addLink(long chatId, String url);

    LinkResponse removeLink(long chatId, String url);

    ListLinkResponse findAllLinks(long chatId);

    boolean regChat(long chatId);

    boolean delChat(long chatId);
}
