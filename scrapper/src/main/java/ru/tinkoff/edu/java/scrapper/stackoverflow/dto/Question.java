package ru.tinkoff.edu.java.scrapper.stackoverflow.dto;

import java.time.OffsetDateTime;

public record Question(String question_id, OffsetDateTime last_edit_date, OffsetDateTime last_activity_date) {
}
