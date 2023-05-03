package ru.tinkoff.edu.java.scrapper.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.tinkoff.edu.java.scrapper.model.Chat;
import ru.tinkoff.edu.java.scrapper.repository.ChatRepository;

import java.util.List;

@RequiredArgsConstructor
public class JdbcChatRepository implements ChatRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Chat add(long chatId) {
        final String sql = "INSERT INTO chat(id) VALUES(?)";
        jdbcTemplate.update(sql, chatId);
        return new Chat(chatId);
    }

    @Override
    public void remove(long chatId) {
        final String sql = "DELETE FROM chat WHERE id = ?";
        jdbcTemplate.update(sql, chatId);
    }

    @Override
    public List<Chat> findAll() {
        final String sql = "SELECT * FROM chat";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Chat.class));
    }

    @Override
    public Chat findById(long id) {
        final String sql = "SELECT * FROM chat WHERE id = ?";
        List<Chat> chats = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Chat.class), id);
        if (chats.isEmpty()) {
            return null;
        }
        return chats.get(0);
    }
}
