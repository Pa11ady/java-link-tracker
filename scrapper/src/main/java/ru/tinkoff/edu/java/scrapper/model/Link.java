package ru.tinkoff.edu.java.scrapper.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "link", schema = "public")
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private OffsetDateTime updated;
}
