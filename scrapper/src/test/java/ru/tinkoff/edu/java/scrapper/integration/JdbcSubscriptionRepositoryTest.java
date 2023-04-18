package ru.tinkoff.edu.java.scrapper.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.model.Link;
import ru.tinkoff.edu.java.scrapper.model.Subscription;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcChatRepository;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcLinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcSubscriptionRepository;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class JdbcSubscriptionRepositoryTest extends IntegrationEnvironment {
    final String URL1 = "https://github.com/sanyarnd/tiff-java-course-2022";
    final String URL2 = "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c";
    final String URL3 = "https://github.com/sanyarnd/tiff-java-course-2023";
    final Long CHAT_ID1 = 101L;
    final Long CHAT_ID2 = 102L;
    final Long CHAT_ID3 = 103L;

    Link link1;
    Link link2;
    Link link3;

    @Autowired
    JdbcSubscriptionRepository jdbcSubscriptionRepository;

    @Autowired
    JdbcLinkRepository jdbcLinkRepository;

    @Autowired
    JdbcChatRepository jdbcChatRepository;

    @BeforeEach
    void setUp() {
        jdbcChatRepository.add(CHAT_ID1);
        jdbcChatRepository.add(CHAT_ID2);
        jdbcChatRepository.add(CHAT_ID3);
        link1 = jdbcLinkRepository.add(URL1);
        link2 = jdbcLinkRepository.add(URL2);
        link3 = jdbcLinkRepository.add(URL3);
    }

    @Test
    void add() {
        //when
        jdbcSubscriptionRepository.add(CHAT_ID1, link1.getId());
        jdbcSubscriptionRepository.add(CHAT_ID1, link2.getId());
        jdbcSubscriptionRepository.add(CHAT_ID2, link3.getId());

        //then
        Subscription result1 = jdbcSubscriptionRepository.find(CHAT_ID1, link1.getId());
        Subscription result2 = jdbcSubscriptionRepository.find(CHAT_ID1, link2.getId());
        Subscription result3 = jdbcSubscriptionRepository.find(CHAT_ID2, link3.getId());

        assertEquals(CHAT_ID1, result1.getChatId());
        assertEquals(link1.getId(), result1.getLinkId());

        assertEquals(CHAT_ID1, result2.getChatId());
        assertEquals(link2.getId(), result2.getLinkId());

        assertEquals(CHAT_ID2, result3.getChatId());
        assertEquals(link3.getId(), result3.getLinkId());
    }

    @Test
    void remove() {
        //given
        jdbcSubscriptionRepository.add(CHAT_ID1, link1.getId());
        jdbcSubscriptionRepository.add(CHAT_ID1, link2.getId());
        jdbcSubscriptionRepository.add(CHAT_ID2, link3.getId());
        Long chatId = jdbcSubscriptionRepository.find(CHAT_ID1, link1.getId()).getChatId();
        assertEquals(CHAT_ID1,chatId);

        //when
        jdbcSubscriptionRepository.remove(CHAT_ID1, link1.getId());

        //then
        assertNull(jdbcSubscriptionRepository.find(CHAT_ID1, link1.getId()));
        assertNotNull(jdbcSubscriptionRepository.find(CHAT_ID1, link2.getId()));
        assertNotNull(jdbcSubscriptionRepository.find(CHAT_ID2, link3.getId()));
    }

    @Test
    void findAll() {
        //given
        jdbcSubscriptionRepository.add(CHAT_ID1, link1.getId());
        jdbcSubscriptionRepository.add(CHAT_ID1, link2.getId());
        jdbcSubscriptionRepository.add(CHAT_ID2, link3.getId());

        //when
        List<Subscription> list = jdbcSubscriptionRepository.findAll();

        //then
        list.sort(Comparator.comparing(Subscription::getChatId).thenComparing(Subscription::getLinkId));
        assertEquals(CHAT_ID1, list.get(0).getChatId());
        assertEquals(link1.getId(), list.get(0).getLinkId());

        assertEquals(CHAT_ID1, list.get(1).getChatId());
        assertEquals(link2.getId(), list.get(1).getLinkId());

        assertEquals(CHAT_ID2, list.get(2).getChatId());
        assertEquals(link3.getId(), list.get(2).getLinkId());

        assertEquals(3, list.size());
    }

    @Test
    void findAllLinks() {
        //given
        jdbcSubscriptionRepository.add(CHAT_ID1, link1.getId());
        jdbcSubscriptionRepository.add(CHAT_ID1, link2.getId());

        //when
        List<Link> links = jdbcSubscriptionRepository.findAllLinks(CHAT_ID1);

        //then
        links.sort(Comparator.comparing(Link::getId));
        assertEquals(link1, links.get(0));
        assertEquals(link2, links.get(1));
        assertEquals(2, links.size());
    }

    @Test
    void find() {
        //given
        jdbcSubscriptionRepository.add(CHAT_ID1, link1.getId());
        jdbcSubscriptionRepository.add(CHAT_ID1, link2.getId());

        //when
        Subscription result = jdbcSubscriptionRepository.find(CHAT_ID1, link1.getId());

        //then
        assertEquals(CHAT_ID1, result.getChatId());
        assertEquals(link1.getId(), result.getLinkId());
    }
}