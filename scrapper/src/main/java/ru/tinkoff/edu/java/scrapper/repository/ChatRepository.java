package ru.tinkoff.edu.java.scrapper.repository;

import ru.tinkoff.edu.java.scrapper.model.Chat;

import java.util.List;

public interface ChatRepository {
    Chat add(long chatId);

    void remove(long chatId);

    List<Chat> findAll();

    Chat findById(long id);
}
