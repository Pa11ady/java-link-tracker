package ru.tinkoff.edu.java.scrapper.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.model.Link;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class LinkRepositoryTest extends IntegrationEnvironment {
    final String URL1 = "https://github.com/sanyarnd/tiff-java-course-2022";
    final String URL2 = "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c";
    final String URL3 = "https://github.com/sanyarnd/tiff-java-course-2023";

    @Autowired
    LinkRepository linkRepository;

    @Test
    void add() {
        //given
        String url = URL1;

        //when
        Link link = linkRepository.add(url);

        //then
        assertEquals(url, link.getUrl());
    }

    @Test
    void remove() {
        // given
        String url = URL1;
        Long linkId = linkRepository.add(url).getId();
        assertEquals(url, linkRepository.findByUrl(url).getUrl());

        // when
        linkRepository.remove(linkId);

        //then
        assertNull(linkRepository.findByUrl(url));
    }

    @Test
    void findAll() {
        //given
        List<Link> links = new ArrayList<>();
        links.add(linkRepository.add(URL1));
        links.add(linkRepository.add(URL2));
        links.add(linkRepository.add(URL3));

        //when
        List<Link> result = linkRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Link::getId))
                .toList();

        //then
        assertIterableEquals(links, result);
    }

    @Test
    void findByUrl() {
        // given
        String url = URL1;
        Link link = linkRepository.add(url);
        assertEquals(url, linkRepository.findByUrl(url).getUrl());

        // when
        Link result = linkRepository.findByUrl(url);

        //then
        assertEquals(link, result);
    }

    @Test
    void findOldLinks() {
        // given
        Link link = linkRepository.add(URL1);
        OffsetDateTime offsetDateTime = OffsetDateTime.now().plusMinutes(10);

        // when
        List<Link> result = linkRepository.findOldLinks(offsetDateTime);

        //then
        assertEquals(link, result.get(0));
    }

    @Test
    void update() {
        // given
        String url = URL2;
        Link link = linkRepository.add(URL1);
        link.setUrl(url);

        // when
        linkRepository.update(link);

        //then
        Link result = linkRepository.findByUrl(url);
        assertEquals(link.getId(), result.getId());
        assertEquals(url, result.getUrl());
    }
}