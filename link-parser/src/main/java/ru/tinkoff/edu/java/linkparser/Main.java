package ru.tinkoff.edu.java.linkparser;

public class Main {
    public static void main(String[] args) {
        String[] urls = {
                "https://github.com/sanyarnd/tiff-java-course-2022",
                "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c",
                "https://stackoverflow.com/search?q=unsupported%20link",
                "https://google.com"
        };

        LinkParser parserChain = new GitLinkParser(new StackOverflowLinkParser(new NullLinkParser()));

        for (String url : urls) {
            LinkInfo linkInfo = parserChain.parseLink(url);

            if (linkInfo != null) {
                if (linkInfo instanceof GitInfo gitInfo) {
                    System.out.println("Git: " + gitInfo.getUserName() + "/" + gitInfo.getRepoName());
                } else if (linkInfo instanceof StackOverflowInfo stackOverflowInfo) {
                    System.out.println("StackOverflow: " + stackOverflowInfo.getId());
                }
            } else {
                System.out.println("Не поддерживается: " + url);
            }
        }
    }
}
