package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.model.Chat;
import ru.tinkoff.edu.java.scrapper.model.Link;
import ru.tinkoff.edu.java.scrapper.repository.ChatRepository;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.SubscriptionRepository;
import ru.tinkoff.edu.java.scrapper.service.SubscriptionService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class JdbcSubscriptionService implements SubscriptionService {
    private final LinkRepository linkRepository;
    private final ChatRepository chatRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Transactional
    @Override
    public Link add(long chatId, String url) {
        Link link = linkRepository.findByUrl(url);
        if (link == null) {
            link = linkRepository.add(url);
        }

        Chat chat = chatRepository.findById(chatId);
        if (chat == null) {
            chatRepository.add(chatId);
        }

        if (subscriptionRepository.find(chatId, link.getId()) == null) {
            subscriptionRepository.add(chatId, link.getId());
        }

        return link;
    }

    @Transactional
    @Override
    public Link remove(long chatId, String url) {
        Link link = linkRepository.findByUrl(url);
        if (link == null) {
            return null;
        }
        subscriptionRepository.remove(chatId, link.getId());
        return link;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Link> listAllLinks(long chatId) {
        return subscriptionRepository.findAllLinks(chatId);
    }
}
