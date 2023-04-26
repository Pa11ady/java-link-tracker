package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.model.Link;

import java.util.List;

public interface SubscriptionService {
    Link add(long chatId, String url);

    Link remove(long chatId, String url);

    List<Link> listAllLinks(long chatId);
}
