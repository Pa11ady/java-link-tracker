package ru.tinkoff.edu.java.linkparser;

import java.net.URI;
import java.net.URISyntaxException;

public class StackOverflowLinkParser implements LinkParser {
    private final LinkParser nextParser;

    public StackOverflowLinkParser(LinkParser nextParser) {
        this.nextParser = nextParser;
    }

    public LinkInfo parseLink(String url) {
        try {
            URI uri = new URI(url);
            String host = uri.getHost();
            String path = uri.getPath();
            if ("stackoverflow.com".equals(host)) {
                String[] segments = path.split("/");
                if (segments.length >= 3) {
                    String id = segments[2];
                    if ("questions".equals(segments[1])) {
                        return new StackOverflowInfoRecord(id);
                    }
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();    //возможно тут лучше было бы логировать
        }

        return nextParser.parseLink(url);
    }
}



