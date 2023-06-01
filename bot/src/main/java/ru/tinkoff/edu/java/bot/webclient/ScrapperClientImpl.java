package ru.tinkoff.edu.java.bot.webclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.webclient.dto.AddLinkRequest;
import ru.tinkoff.edu.java.bot.webclient.dto.LinkResponse;
import ru.tinkoff.edu.java.bot.webclient.dto.ListLinkResponse;

@Component
public class ScrapperClientImpl implements ScrapperClient {
    private static final String TG_CHAT_ID = "Tg-Chat-Id";
    private static final String CHATS_URI = "/tg-chat/%s";
    private static final String LINKS = "/links";
    private final WebClient client;

    @Autowired
    public ScrapperClientImpl(WebClient client) {
        this.client = client;
    }

    @Override
    public LinkResponse addLink(long chatId, String url) {
        return client
                .post()
                .uri(LINKS)
                .header(TG_CHAT_ID, Long.toString(chatId))
                .body(BodyInserters.fromValue(new AddLinkRequest(url)))
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .onErrorResume(WebClientResponseException.class, (ex) -> Mono.empty())
                .block();
    }

    @Override
    public LinkResponse removeLink(long chatId, String url) {
        return client
                .method(HttpMethod.DELETE)
                .uri(LINKS)
                .header(TG_CHAT_ID, Long.toString(chatId))
                .body(BodyInserters.fromValue(new AddLinkRequest(url)))
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .onErrorResume(WebClientResponseException.class, (ex) -> Mono.empty())
                .block();
    }

    @Override
    public ListLinkResponse findAllLinks(long chatId) {
        return client
                .get()
                .uri(LINKS)
                .header(TG_CHAT_ID, Long.toString(chatId))
                .retrieve()
                .bodyToMono(ListLinkResponse.class)
                .onErrorResume(WebClientResponseException.class, (ex) -> Mono.empty())
                .block();
    }

    @Override
    public boolean regChat(long chatId) {
        return Boolean.TRUE.equals(client
                .post()
                .uri(CHATS_URI.formatted(chatId))
                .retrieve()
                .toBodilessEntity()
                .flatMap((clientResponse) -> Mono.just(!clientResponse.getStatusCode().isError()))
                .block());
    }

    @Override
    public boolean delChat(long chatId) {
        return Boolean.TRUE.equals(client
                .delete()
                .uri(CHATS_URI.formatted(chatId))
                .retrieve()
                .toBodilessEntity()
                .flatMap((clientResponse) -> Mono.just(!clientResponse.getStatusCode().isError()))
                .block());
    }
}
