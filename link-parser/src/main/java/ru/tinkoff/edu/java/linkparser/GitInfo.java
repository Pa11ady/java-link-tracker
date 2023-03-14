package ru.tinkoff.edu.java.linkparser;

public sealed interface GitInfo extends LinkInfo permits GitInfoRecord {
    String getUserName();

    String getRepoName();
}