package ru.tinkoff.edu.java.linkparser.dto;

public sealed interface LinkInfo permits GitInfo, StackOverflowInfo {
    // общие методы для всех типов ссылок
}
