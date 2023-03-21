package ru.tinkoff.edu.java.linkparser.parser;

import ru.tinkoff.edu.java.linkparser.dto.LinkInfo;
import ru.tinkoff.edu.java.linkparser.dto.StackOverflowInfo;

import java.net.URI;
import java.net.URISyntaxException;

public class StackOverflowLinkParser extends AbstractParser {

    public StackOverflowLinkParser(LinkParser nextParser) {
        super(nextParser);
    }

    @Override
    public LinkInfo parse(String url) {
        LinkInfo linkInfo = null;
        try {
            URI uri = new URI(url);
            String host = uri.getHost();
            String path = uri.getPath();
            if ("stackoverflow.com".equals(host)) {
                String[] segments = path.split("/");
                if (segments.length >= 3) {
                    String id = segments[2];
                    if ("questions".equals(segments[1])) {
                        linkInfo = new StackOverflowInfo(id);
                    }
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();    //возможно тут лучше было бы логировать
        }

        return linkInfo;
    }
}



