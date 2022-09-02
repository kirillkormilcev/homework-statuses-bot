package app.filatov.homeworkstatusesbot.service;

import app.filatov.homeworkstatusesbot.model.repository.HomeworkRepository;
import app.filatov.homeworkstatusesbot.model.repository.UserRepository;
import app.filatov.homeworkstatusesbot.service.loader.LoaderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ScheduledService {
    private final LoaderService loaderService;
    private final UserRepository userRepository;
    private final HomeworkRepository homeworkRepository;

    public ScheduledService(LoaderService loaderService,
                            UserRepository userRepository,
                            HomeworkRepository homeworkRepository) {
        this.loaderService = loaderService;
        this.userRepository = userRepository;
        this.homeworkRepository = homeworkRepository;
    }

    @Scheduled(fixedRate = 60_000)
    public void updateHomeworkStatus() {
        // Зарегистрированные пользователи, подписанные на обновления
        userRepository.findAll().stream()
                // Их домашние задания
                .peek(user -> log.info("Получаем домашние задания для пользователя с id={}", user.getId()))
                .map(loaderService::getHomeworks)
                .peek(homeworks -> log.info("Получено {} домашних заданий", homeworks.size()))
                // Сохраняем в базу
                .forEach(homeworkRepository::saveAll);
    }
}
