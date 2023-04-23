package ru.tinkoff.edu.java.scrapper.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.model.Chat;
import ru.tinkoff.edu.java.scrapper.repository.ChatRepository;

import java.util.List;

@RequiredArgsConstructor
public class JpaChatRepository implements ChatRepository {
    private final EntityManager entityManager;

    @Override
    public Chat add(long chatId) {
        Chat chat = new Chat(chatId);
        return entityManager.merge(chat);
    }

    @Override
    public void remove(long chatId) {
        Chat chat = findById(chatId);
        if (chat != null) {
            entityManager.remove(chat);
        }
    }

    @Override
    public List<Chat> findAll() {
        TypedQuery<Chat> query = entityManager.createQuery("SELECT ch FROM Chat ch", Chat.class);
        return query.getResultList();
    }

    @Override
    public Chat findById(long id) {
        return entityManager.find(Chat.class, id);
    }
}
