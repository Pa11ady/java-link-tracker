package ru.tinkoff.edu.java.linkparser;

public class NullLinkParser implements LinkParser {
    public LinkInfo parseLink(String url) {
        return null;
    }
}
