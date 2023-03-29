package ru.tinkoff.edu.java.linkparser.dto;

public record GitInfo(String userName, String repoName) implements LinkInfo {
}