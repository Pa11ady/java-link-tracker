package ru.tinkoff.edu.java.scrapper.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Link {
    private Long id;
    private String url;
    private OffsetDateTime updated;
}
