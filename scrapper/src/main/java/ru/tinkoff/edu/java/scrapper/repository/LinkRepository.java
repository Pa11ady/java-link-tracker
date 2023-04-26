package ru.tinkoff.edu.java.scrapper.repository;

import ru.tinkoff.edu.java.scrapper.model.Link;

import java.time.OffsetDateTime;
import java.util.List;

public interface LinkRepository {
    Link add(String url);

    void remove(Long linkId);

    List<Link> findAll();

    Link findByUrl(String url);

    List<Link> findOldLinks(OffsetDateTime offsetDateTime);

    Link update(Link link);
}
