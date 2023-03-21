package ru.tinkoff.edu.java.linkparser;

import ru.tinkoff.edu.java.linkparser.dto.GitInfo;
import ru.tinkoff.edu.java.linkparser.dto.LinkInfo;
import ru.tinkoff.edu.java.linkparser.dto.StackOverflowInfo;
import ru.tinkoff.edu.java.linkparser.parser.GitLinkParser;
import ru.tinkoff.edu.java.linkparser.parser.LinkParser;
import ru.tinkoff.edu.java.linkparser.parser.StackOverflowLinkParser;

public class Main {
    public static void main(String[] args) {
        String[] urls = {
                "https://github.com/sanyarnd/tiff-java-course-2022",
                "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c",
                "https://stackoverflow.com/search?q=unsupported%20link",
                "https://google.com"
        };

        LinkParser parserChain = new GitLinkParser(new StackOverflowLinkParser(null));

        for (String url : urls) {
            LinkInfo linkInfo = parserChain.parseLink(url);

            if (linkInfo != null) {
                if (linkInfo instanceof GitInfo gitInfo) {
                    System.out.println("Git: " + gitInfo.userName() + "/" + gitInfo.repoName());
                } else if (linkInfo instanceof StackOverflowInfo stackOverflowInfo) {
                    System.out.println("StackOverflow: " + stackOverflowInfo.id());
                }
            } else {
                System.out.println("Не поддерживается: " + url);
            }
        }
    }
}
