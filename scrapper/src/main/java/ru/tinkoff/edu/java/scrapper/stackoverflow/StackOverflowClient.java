package ru.tinkoff.edu.java.scrapper.stackoverflow;

import ru.tinkoff.edu.java.scrapper.stackoverflow.dto.QuestionResponse;

public interface StackOverflowClient {
    QuestionResponse findQuestion(String id);
}
