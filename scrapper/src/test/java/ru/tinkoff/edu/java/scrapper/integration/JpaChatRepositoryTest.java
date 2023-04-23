package ru.tinkoff.edu.java.scrapper.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.model.Chat;
import ru.tinkoff.edu.java.scrapper.repository.jpa.JpaChatRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class JpaChatRepositoryTest extends IntegrationEnvironment {

    @Autowired
    JpaChatRepository jpaChatRepository;

    @Test
    void add() {
        // given
        Long chatId = 100500L;

        // when
        Chat chatAdd = jpaChatRepository.add(chatId);

        //then
        assertEquals(chatId, chatAdd.getId());
        Chat chatFind = jpaChatRepository.findById(chatId);
        assertEquals(chatId, chatFind.getId());
    }

    @Test
    void remove() {
        // given
        Long chatId = 100500L;
        jpaChatRepository.add(chatId);
        assertEquals(chatId, jpaChatRepository.findById(chatId).getId());

        // when
        jpaChatRepository.remove(chatId);

        //then
        assertNull(jpaChatRepository.findById(chatId));
    }

    @Test
    void findAll() {
        //given
        List<Long> ids = List.of(10L, 20L, 30L);
        ids.forEach(x -> jpaChatRepository.add(x));

        //when
        List<Long> result = jpaChatRepository.findAll().stream().map(Chat::getId).sorted().toList();

        //then
        assertIterableEquals(ids, result);
    }

    @Test
    void findById() {
        // given
        Long chatId = 1001L;
        jpaChatRepository.add(chatId);
        assertEquals(chatId, jpaChatRepository.findById(chatId).getId());

        // when
        Long actualId = jpaChatRepository.findById(chatId).getId();

        //then
        assertEquals(chatId, actualId);
    }
}