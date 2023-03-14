package ru.tinkoff.edu.java.linkparser;

import java.net.URI;
import java.net.URISyntaxException;

public class GitLinkParser implements LinkParser {
    private final LinkParser nextParser;

    public GitLinkParser(LinkParser nextParser) {
        this.nextParser = nextParser;
    }

    public LinkInfo parseLink(String url) {
        try {
            URI uri = new URI(url);
            String host = uri.getHost();
            String path = uri.getPath();
            if ("github.com".equals(host)) {
                String[] segments = path.split("/");
                if (segments.length >= 3) {
                    String userName = segments[1];
                    String repoName = segments[2];
                    return new GitInfoRecord(userName, repoName);
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return nextParser.parseLink(url);
    }
}
