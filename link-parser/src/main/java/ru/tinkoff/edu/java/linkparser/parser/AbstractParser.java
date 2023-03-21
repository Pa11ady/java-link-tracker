package ru.tinkoff.edu.java.linkparser.parser;

import ru.tinkoff.edu.java.linkparser.dto.LinkInfo;

public abstract class AbstractParser implements LinkParser {
    private final LinkParser nextParser;

    public AbstractParser(LinkParser nextParser) {
        this.nextParser = nextParser;
    }

    @Override
    public LinkInfo parseLink(String url) {
        LinkInfo linkInfo = parse(url);
        //Или url успешно обработан или нет больше обработчиков
        if (linkInfo != null || nextParser == null) {
            return linkInfo;
        }
        return nextParser.parseLink(url);
    }

    abstract LinkInfo parse(String url);
}
