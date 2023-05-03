package ru.tinkoff.edu.java.scrapper.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.model.Link;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;

import java.time.OffsetDateTime;
import java.util.List;

@RequiredArgsConstructor
public class JpaLinkRepository implements LinkRepository {
    private final EntityManager entityManager;

    @Override
    public Link add(String url) {
        Link link = new Link();
        link.setUrl(url);
        link.setUpdated(OffsetDateTime.now());
        entityManager.persist(link);
        return findByUrl(url);
    }

    @Override
    public void remove(Long linkId) {
        Link link = findById(linkId);
        if (link != null) {
            entityManager.remove(link);
        }
    }

    @Override
    public List<Link> findAll() {
        return entityManager.createQuery("SELECT l from Link l", Link.class).getResultList();
    }

    @Override
    public Link findByUrl(String url) {
        TypedQuery<Link> query = entityManager.createQuery("SELECT l from Link l WHERE url = :url", Link.class);
        query.setParameter("url", url);
        List<Link> links = query.getResultList();
        if (links.isEmpty()) {
            return null;
        }
        return links.get(0);
    }

    @Override
    public List<Link> findOldLinks(OffsetDateTime offsetDateTime) {
        TypedQuery<Link> query = entityManager.createQuery("SELECT l from Link l WHERE updated < :date", Link.class);
        query.setParameter("date", offsetDateTime);
        return query.getResultList();
    }

    @Override
    public Link update(Link link) {
        return entityManager.merge(link);
    }

    @Override
    public void updateToCurrentDate(Long linkId) {
        Link link = findById(linkId);
        if (link != null) {
            link.setUpdated(OffsetDateTime.now());
            update(link);
        }
    }

    public Link findById(long linkId) {
        return entityManager.find(Link.class, linkId);
    }
}
