package ru.tinkoff.edu.java.scrapper.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.model.Chat;
import ru.tinkoff.edu.java.scrapper.repository.ChatRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ChatRepositoryTest extends IntegrationEnvironment {

    @Autowired
    ChatRepository chatRepository;

    @Test
    void add() {
        // given
        Long chatId = 100500L;

        // when
        Chat chatAdd = chatRepository.add(chatId);

        //then
        assertEquals(chatId, chatAdd.getId());
        Chat chatFind = chatRepository.findById(chatId);
        assertEquals(chatId, chatFind.getId());
    }

    @Test
    void remove() {
        // given
        Long chatId = 100500L;
        chatRepository.add(chatId);
        assertEquals(chatId, chatRepository.findById(chatId).getId());

        // when
        chatRepository.remove(chatId);

        //then
        assertNull(chatRepository.findById(chatId));
    }

    @Test
    void findAll() {
        //given
        List<Long> ids = List.of(10L, 20L, 30L);
        ids.forEach(x -> chatRepository.add(x));

        //when
        List<Long> result = chatRepository.findAll().stream().map(Chat::getId).sorted().toList();

        //then
        assertIterableEquals(ids, result);
    }

    @Test
    void findById() {
        // given
        Long chatId = 1001L;
        chatRepository.add(chatId);
        assertEquals(chatId, chatRepository.findById(chatId).getId());

        // when
        Long actualId = chatRepository.findById(chatId).getId();

        //then
        assertEquals(chatId, actualId);
    }
}