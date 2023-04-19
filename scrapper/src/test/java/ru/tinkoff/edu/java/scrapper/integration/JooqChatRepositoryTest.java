package ru.tinkoff.edu.java.scrapper.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.model.Chat;
import ru.tinkoff.edu.java.scrapper.repository.jooq.JooqChatRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class JooqChatRepositoryTest extends IntegrationEnvironment {

    @Autowired
    JooqChatRepository jooqChatRepository;

    @Test
    void add() {
        // given
        Long chatId = 100500L;

        // when
        Chat chatAdd = jooqChatRepository.add(chatId);

        //then
        assertEquals(chatId, chatAdd.getId());
        Chat chatFind = jooqChatRepository.findById(chatId);
        assertEquals(chatId, chatFind.getId());
    }

    @Test
    void remove() {
        // given
        Long chatId = 100500L;
        jooqChatRepository.add(chatId);
        assertEquals(chatId, jooqChatRepository.findById(chatId).getId());

        // when
        jooqChatRepository.remove(chatId);

        //then
        assertNull(jooqChatRepository.findById(chatId));
    }

    @Test
    void findAll() {
        //given
        List<Long> ids = List.of(10L, 20L, 30L);
        ids.forEach(x -> jooqChatRepository.add(x));

        //when
        List<Long> result = jooqChatRepository.findAll().stream().map(Chat::getId).sorted().toList();

        //then
        assertIterableEquals(ids, result);
    }

    @Test
    void findById() {
        // given
        Long chatId = 1001L;
        jooqChatRepository.add(chatId);
        assertEquals(chatId, jooqChatRepository.findById(chatId).getId());

        // when
        Long actualId = jooqChatRepository.findById(chatId).getId();

        //then
        assertEquals(chatId, actualId);
    }
}