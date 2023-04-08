package ru.tinkoff.edu.java.bot.webclient.dto;

import java.util.List;

public record ListLinkResponse(List<LinkResponse> links, int size) {
}
