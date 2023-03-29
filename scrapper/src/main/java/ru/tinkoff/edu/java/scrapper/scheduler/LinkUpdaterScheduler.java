package ru.tinkoff.edu.java.scrapper.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class LinkUpdaterScheduler {
    //@Scheduled(fixedDelayString = "${app.scheduler.interval}")
    @Scheduled(fixedDelayString = "#{@scheduler1.interval}")

    public void update() {
        log.info("Обновляем ссылку...");

    }
}
