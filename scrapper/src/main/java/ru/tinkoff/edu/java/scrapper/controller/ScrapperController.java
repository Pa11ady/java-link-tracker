package ru.tinkoff.edu.java.scrapper.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.model.Link;
import ru.tinkoff.edu.java.scrapper.service.ChatService;
import ru.tinkoff.edu.java.scrapper.service.SubscriptionService;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ScrapperController {
    private final ChatService chatService;
    private final SubscriptionService subscriptionService;

    @PostMapping("/tg-chat/{id}")
    public void registerChat(@PathVariable Long id) {
        log.info("Зарегистрировать чат id=" + id);
        chatService.register(id);
    }

    @DeleteMapping("/tg-chat/{id}")
    public void deleteChat(@PathVariable Long id) {
        log.info("Удалить чат id=" + id);
        chatService.unregister(id);
    }

    @GetMapping("/links")
    public ListLinksResponse findAllLinks(@RequestHeader("Tg-Chat-Id") Long id) {
        log.info("Получить все отслеживаемые ссылки id=" + id);

        List<LinkResponse> links = subscriptionService.listAllLinks(id).stream()
                .map(link -> new LinkResponse(link.getId(), URI.create(link.getUrl()))).toList();
        return new ListLinksResponse(links, links.size());
    }

    @PostMapping("/links")
    public LinkResponse createLink(@RequestHeader("Tg-Chat-Id") Long id, @RequestBody AddLinkRequest addLinkRequest) {
        log.info("Получить все отслеживаемые ссылки id=" + id);
        log.info(addLinkRequest.toString());

        Link link = subscriptionService.add(id, addLinkRequest.link());
        return new LinkResponse(link.getId(), URI.create(link.getUrl()));
    }

    @DeleteMapping("/links")
    public LinkResponse deleteLink(@RequestHeader("Tg-Chat-Id") Long id,
                                   @RequestBody RemoveLinkRequest removeLinkRequest) {
        log.info("Убрать отслеживание ссылки id=" + id);

        Link link = subscriptionService.remove(id, removeLinkRequest.link());
        return new LinkResponse(link.getId(), URI.create(link.getUrl()));
    }
}
