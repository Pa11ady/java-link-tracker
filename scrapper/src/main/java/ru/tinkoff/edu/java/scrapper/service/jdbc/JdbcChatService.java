package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.repository.ChatRepository;
import ru.tinkoff.edu.java.scrapper.service.ChatService;

@RequiredArgsConstructor
@Service
public class JdbcChatService implements ChatService {
    private final ChatRepository chatRepository;

    @Transactional
    @Override
    public void register(long chatId) {
        if (chatRepository.findById(chatId) == null) {
            chatRepository.add(chatId);
        }
    }

    @Transactional
    @Override
    public void unregister(long chatId) {
        chatRepository.remove(chatId);
    }
}
