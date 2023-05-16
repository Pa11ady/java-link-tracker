package ru.tinkoff.edu.java.scrapper.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.model.Chat;
import ru.tinkoff.edu.java.scrapper.model.Link;
import ru.tinkoff.edu.java.scrapper.model.Subscription;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class SubscriptionRepository implements ru.tinkoff.edu.java.scrapper.repository.SubscriptionRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void add(Long chatId, Long linkId) {
        final String sql = "INSERT INTO chat_link VALUES(?, ?)";
        jdbcTemplate.update(sql, chatId, linkId);
    }

    @Override
    public void remove(Long chatId, Long linkId) {
        final String sql = "DELETE FROM chat_link WHERE (chat_id, link_id) = (?, ?)";
        jdbcTemplate.update(sql, chatId, linkId);
    }

    @Override
    public List<Subscription> findAll() {
        final String sql = "SELECT * FROM chat_link";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Subscription.class));
    }

    @Override
    public List<Link> findAllLinks(Long chatId) {
        final String sql = "SELECT link.* FROM link INNER JOIN chat_link ON link.id = link_id WHERE chat_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Link.class), chatId);
    }

    @Override
    public Subscription find(long chatId, Long linkId) {
        final String sql = "SELECT * FROM chat_link WHERE (chat_id, link_id) = (?, ?)";
        List<Subscription> subscriptions = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Subscription.class),
                chatId, linkId);
        if (subscriptions.isEmpty()) {
            return null;
        }
        return subscriptions.get(0);
    }

    @Override
    public List<Chat> findAllByLink(long linkId) {
        final String sql = "SELECT chat.* FROM chat JOIN chat_link ON chat.id = chat_id WHERE link_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Chat.class), linkId);
    }
}
