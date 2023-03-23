package ru.tinkoff.edu.java.scrapper.stackoverflow.dto;

import java.util.List;

public record QuestionResponse(List<Question> items) {
}
