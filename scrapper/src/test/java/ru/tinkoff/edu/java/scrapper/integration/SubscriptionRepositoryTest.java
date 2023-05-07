package ru.tinkoff.edu.java.scrapper.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.model.Link;
import ru.tinkoff.edu.java.scrapper.model.Subscription;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.ChatRepository;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.LinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.SubscriptionRepository;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class SubscriptionRepositoryTest extends IntegrationEnvironment {
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
    SubscriptionRepository subscriptionRepository;

    @Autowired
    LinkRepository linkRepository;

    @Autowired
    ChatRepository chatRepository;

    @BeforeEach
    void setUp() {
        chatRepository.add(CHAT_ID1);
        chatRepository.add(CHAT_ID2);
        chatRepository.add(CHAT_ID3);
        link1 = linkRepository.add(URL1);
        link2 = linkRepository.add(URL2);
        link3 = linkRepository.add(URL3);
    }

    @Test
    void add() {
        //when
        subscriptionRepository.add(CHAT_ID1, link1.getId());
        subscriptionRepository.add(CHAT_ID1, link2.getId());
        subscriptionRepository.add(CHAT_ID2, link3.getId());

        //then
        Subscription result1 = subscriptionRepository.find(CHAT_ID1, link1.getId());
        Subscription result2 = subscriptionRepository.find(CHAT_ID1, link2.getId());
        Subscription result3 = subscriptionRepository.find(CHAT_ID2, link3.getId());

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
        subscriptionRepository.add(CHAT_ID1, link1.getId());
        subscriptionRepository.add(CHAT_ID1, link2.getId());
        subscriptionRepository.add(CHAT_ID2, link3.getId());
        Long chatId = subscriptionRepository.find(CHAT_ID1, link1.getId()).getChatId();
        assertEquals(CHAT_ID1,chatId);

        //when
        subscriptionRepository.remove(CHAT_ID1, link1.getId());

        //then
        assertNull(subscriptionRepository.find(CHAT_ID1, link1.getId()));
        assertNotNull(subscriptionRepository.find(CHAT_ID1, link2.getId()));
        assertNotNull(subscriptionRepository.find(CHAT_ID2, link3.getId()));
    }

    @Test
    void findAll() {
        //given
        subscriptionRepository.add(CHAT_ID1, link1.getId());
        subscriptionRepository.add(CHAT_ID1, link2.getId());
        subscriptionRepository.add(CHAT_ID2, link3.getId());

        //when
        List<Subscription> list = subscriptionRepository.findAll();

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
        subscriptionRepository.add(CHAT_ID1, link1.getId());
        subscriptionRepository.add(CHAT_ID1, link2.getId());

        //when
        List<Link> links = subscriptionRepository.findAllLinks(CHAT_ID1);

        //then
        links.sort(Comparator.comparing(Link::getId));
        assertEquals(link1, links.get(0));
        assertEquals(link2, links.get(1));
        assertEquals(2, links.size());
    }

    @Test
    void find() {
        //given
        subscriptionRepository.add(CHAT_ID1, link1.getId());
        subscriptionRepository.add(CHAT_ID1, link2.getId());

        //when
        Subscription result = subscriptionRepository.find(CHAT_ID1, link1.getId());

        //then
        assertEquals(CHAT_ID1, result.getChatId());
        assertEquals(link1.getId(), result.getLinkId());
    }
}