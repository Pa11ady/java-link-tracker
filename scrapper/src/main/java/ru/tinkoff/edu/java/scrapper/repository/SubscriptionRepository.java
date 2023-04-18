package ru.tinkoff.edu.java.scrapper.repository;

import ru.tinkoff.edu.java.scrapper.model.Chat;
import ru.tinkoff.edu.java.scrapper.model.Link;
import ru.tinkoff.edu.java.scrapper.model.Subscription;

import java.util.List;

public interface SubscriptionRepository {
    void add(Long chatId, Long linkId);

    void remove(Long chatId, Long linkId);

    List<Subscription> findAll();

    List<Link> findAllLinks(Long chatId);

    Subscription find(long chatId, Long linkId);

    List<Chat> findAllByLink(long linkId);
}
