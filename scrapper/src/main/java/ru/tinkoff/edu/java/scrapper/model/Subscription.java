package ru.tinkoff.edu.java.scrapper.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Subscription {
    private Long chatId;
    private Long linkId;
}
