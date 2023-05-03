package ru.tinkoff.edu.java.scrapper.repository.jooq;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.model.Chat;
import ru.tinkoff.edu.java.scrapper.repository.ChatRepository;

import java.util.List;

import static ru.tinkoff.edu.java.scrapper.model.jooq.tables.Chat.CHAT;

@RequiredArgsConstructor
@Repository
public class JooqChatRepository implements ChatRepository {
    private final DSLContext context;

    @Override
    public Chat add(long chatId) {
        return context.insertInto(CHAT, CHAT.ID)
                .values(chatId)
                .returning(CHAT.fields())
                .fetchAnyInto(Chat.class);
    }

    @Override
    public void remove(long chatId) {
        context.deleteFrom(CHAT).where(CHAT.ID.eq(chatId)).execute();
    }

    @Override
    public List<Chat> findAll() {
        return context.select(CHAT.fields()).from(CHAT).fetchInto(Chat.class);
    }

    @Override
    public Chat findById(long id) {
        List<Chat> chats = context.select(CHAT.fields()).from(CHAT).where(CHAT.ID.eq(id)).fetchInto(Chat.class);
        if (chats.isEmpty()) {
            return null;
        }
        return chats.get(0);
    }
}
