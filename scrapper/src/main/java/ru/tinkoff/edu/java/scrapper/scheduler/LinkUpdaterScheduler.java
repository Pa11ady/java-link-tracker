package ru.tinkoff.edu.java.scrapper.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.service.UpdateService;

@Slf4j
@Component
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    private final UpdateService updateService;

    //@Scheduled(fixedDelayString = "${app.scheduler.interval}")
    @Scheduled(fixedDelayString = "#{@scheduler.interval}")

    public void update() {
        updateService.update();
        log.info("Проверяем обновления...");
    }
}
