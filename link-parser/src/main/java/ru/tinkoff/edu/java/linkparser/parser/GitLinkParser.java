package ru.tinkoff.edu.java.linkparser.parser;

import ru.tinkoff.edu.java.linkparser.dto.GitInfo;
import ru.tinkoff.edu.java.linkparser.dto.LinkInfo;

import java.net.URI;
import java.net.URISyntaxException;

public class GitLinkParser extends AbstractParser {
    public GitLinkParser(LinkParser nextParser) {
        super(nextParser);
    }

    @Override
    public LinkInfo parse(String url) {
        LinkInfo linkInfo = null;
        try {
            URI uri = new URI(url);
            String host = uri.getHost();
            String path = uri.getPath();
            if ("github.com".equals(host)) {
                String[] segments = path.split("/");
                if (segments.length >= 3) {
                    String userName = segments[1];
                    String repoName = segments[2];
                    linkInfo = new GitInfo(userName, repoName);
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return linkInfo;
    }
}
