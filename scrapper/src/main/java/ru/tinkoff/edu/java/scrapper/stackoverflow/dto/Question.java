package ru.tinkoff.edu.java.scrapper.stackoverflow.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record Question(
        @JsonProperty("questionId") String questionId,
        @JsonProperty("last_edit_date") OffsetDateTime lastEditDate,
        @JsonProperty("last_activity_date") OffsetDateTime lastActivityDate) {
}
