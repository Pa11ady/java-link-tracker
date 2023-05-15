package ru.tinkoff.edu.java.scrapper.stackoverflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.stackoverflow.dto.QuestionResponse;

@Component
public class StackOverflowClientImpl implements StackOverflowClient {
    private final WebClient webClient;

    @Autowired
    public StackOverflowClientImpl(@Qualifier("webCliStackOverflow") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public QuestionResponse findQuestion(String id) {
        return webClient
                .get()
                .uri("/2.3/questions/{id}?y=&site=stackoverflow", id)
                .retrieve()
                .bodyToMono(QuestionResponse.class)
                .block();
    }

}
