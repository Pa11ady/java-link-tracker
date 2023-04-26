package ru.tinkoff.edu.java.scrapper.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.model.Chat;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcChatRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class JdbcChatRepositoryTest extends IntegrationEnvironment {

    @Autowired
    JdbcChatRepository jdbcChatRepository;

    @Test
    void add() {
        // given
        Long chatId = 100500L;

        // when
        Chat chatAdd = jdbcChatRepository.add(chatId);

        //then
        assertEquals(chatId, chatAdd.getId());
        Chat chatFind = jdbcChatRepository.findById(chatId);
        assertEquals(chatId, chatFind.getId());
    }

    @Test
    void remove() {
        // given
        Long chatId = 100500L;
        jdbcChatRepository.add(chatId);
        assertEquals(chatId, jdbcChatRepository.findById(chatId).getId());

        // when
        jdbcChatRepository.remove(chatId);

        //then
        assertNull(jdbcChatRepository.findById(chatId));
    }

    @Test
    void findAll() {
        //given
        List<Long> ids = List.of(10L, 20L, 30L);
        ids.forEach(x -> jdbcChatRepository.add(x));

        //when
        List<Long> result = jdbcChatRepository.findAll().stream().map(Chat::getId).sorted().toList();

        //then
        assertIterableEquals(ids, result);
    }

    @Test
    void findById() {
        // given
        Long chatId = 1001L;
        jdbcChatRepository.add(chatId);
        assertEquals(chatId, jdbcChatRepository.findById(chatId).getId());

        // when
        Long actualId = jdbcChatRepository.findById(chatId).getId();

        //then
        assertEquals(chatId, actualId);
    }
}