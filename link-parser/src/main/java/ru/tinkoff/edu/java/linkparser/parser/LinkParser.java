package ru.tinkoff.edu.java.linkparser.parser;

import ru.tinkoff.edu.java.linkparser.dto.LinkInfo;

public interface LinkParser {
    LinkInfo parseLink(String url);
}
