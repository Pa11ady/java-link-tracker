package ru.tinkoff.edu.java.scrapper.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ScrapperController {

    @PostMapping("/tg-chat/{id}")
    public void registerChat(@PathVariable Long id) {
        log.info("Зарегистрировать чат id=" + id);
    }

    @DeleteMapping("/tg-chat/{id}")
    public void deleteChat(@PathVariable Long id) {
        log.info("Удалить чат id=" + id);
    }

    @GetMapping("/links")
    public ListLinksResponse findAllLinks(@RequestHeader("Tg-Chat-Id") Long id) {
        log.info("Получить все отслеживаемые ссылки id=" + id);
        LinkResponse link1 = new LinkResponse(1L, "https://github.com/sanyarnd/tiff-java-course-2022");
        LinkResponse link2 = new LinkResponse(2L,
                "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c");
        List<LinkResponse> links = List.of(link1, link2);
        ListLinksResponse listLinksResponse = new ListLinksResponse(links, links.size());
        return  listLinksResponse;
    }

    @PostMapping("/links")
    public LinkResponse createLink(@RequestHeader("Tg-Chat-Id") Long id, @RequestBody AddLinkRequest addLinkRequest) {
        log.info("Получить все отслеживаемые ссылки id=" + id);
        log.info(addLinkRequest.toString());
        LinkResponse linkResponse = new LinkResponse(1L, addLinkRequest.link());
        return linkResponse;
    }

    @DeleteMapping("/links")
    public LinkResponse deleteLink(@RequestHeader("Tg-Chat-Id") Long id) {
        log.info("Убрать отслеживание ссылки id=" + id);
        return new LinkResponse(1L, "https://github.com/sanyarnd/tiff-java-course-2022");
    }
}
