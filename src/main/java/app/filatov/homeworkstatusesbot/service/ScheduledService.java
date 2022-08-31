package app.filatov.homeworkstatusesbot.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ScheduledService {

    @Scheduled(fixedRate = 60_000)
    public void updateStatus() {
        log.info("Обновление статуса");
    }
}
