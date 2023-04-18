package ru.tinkoff.edu.java.scrapper.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.model.Link;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;

import java.time.OffsetDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class JdbcLinkRepository implements LinkRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Link add(String url) {
        final String sql = "INSERT INTO link(url) VALUES(?)";
        jdbcTemplate.update(sql, url);
        return findByUrl(url);
    }

    @Override
    public void remove(Long linkId) {
        final String sql = "DELETE FROM link WHERE id = ?";
        jdbcTemplate.update(sql, linkId);
    }

    @Override
    public List<Link> findAll() {
        final String sql = "SELECT * FROM link";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Link.class));
    }

    @Override
    public Link findByUrl(String url) {
        final String sql = "SELECT * FROM link WHERE url = ?";
        List<Link> links = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Link.class), url);
        if (links.isEmpty()) {
            return null;
        }
        return links.get(0);
    }

    @Override
    public List<Link> findOldLinks(OffsetDateTime offsetDateTime) {
        final String sql = "SELECT * FROM link WHERE updated < ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Link.class), offsetDateTime);
    }

    @Override
    public Link update(Link link) {
        final String sql = "UPDATE link SET url = ?, updated = ? WHERE id = ?";
        jdbcTemplate.update(sql, link.getUrl(), link.getUpdated(), link.getId());
        return link;
    }
}
