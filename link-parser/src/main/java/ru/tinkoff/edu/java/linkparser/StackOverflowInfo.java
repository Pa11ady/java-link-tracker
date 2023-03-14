package ru.tinkoff.edu.java.linkparser;

public sealed interface StackOverflowInfo extends LinkInfo permits StackOverflowInfoRecord {
    String getId();
}