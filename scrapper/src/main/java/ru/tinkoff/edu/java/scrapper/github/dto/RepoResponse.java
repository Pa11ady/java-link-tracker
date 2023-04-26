package ru.tinkoff.edu.java.scrapper.github.dto;

import java.time.OffsetDateTime;

public record RepoResponse(String full_name, OffsetDateTime updated_at, OffsetDateTime pushed_at) {
}
