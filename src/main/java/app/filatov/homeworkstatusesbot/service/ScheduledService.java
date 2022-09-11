package app.filatov.homeworkstatusesbot.service;

import app.filatov.homeworkstatusesbot.model.Homework;
import app.filatov.homeworkstatusesbot.model.repository.HomeworkRepository;
import app.filatov.homeworkstatusesbot.model.repository.UserRepository;
import app.filatov.homeworkstatusesbot.service.loader.LoaderService;
import app.filatov.homeworkstatusesbot.service.update.NewChangesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class ScheduledService {
    private final LoaderService loaderService;
    private final UserRepository userRepository;
    private final HomeworkRepository homeworkRepository;
    private final NewChangesService newChangesService;

    /*public ScheduledService(LoaderService loaderService,
                            UserRepository userRepository,
                            HomeworkRepository homeworkRepository) {
        this.loaderService = loaderService;
        this.userRepository = userRepository;
        this.homeworkRepository = homeworkRepository;
    }*/

    @Scheduled(fixedRate = 60_000)
    public void updateHomeworkStatus() {
        userRepository.findReadyToUpdateUser().stream()
                // Их домашние задания
                .peek(user -> log.info("Получаем домашние задания для пользователя с id={}", user.getId()))
                .forEach(user -> {

                    try {
                        // Сохраняем в базу
                        List<Homework> homeworks = loaderService.getHomeworks(user);
                        log.info("Для пользователя с id={} получено {} домашних заданий", user.getId(), homeworks.size());

                        newChangesService.checkChangesInHomeworks(homeworks, user.getId());

                        homeworks = homeworkRepository.saveAll(homeworks);
                        log.info("Для пользователя с id={} сохранено в базе {} домашних заданий", user.getId(), homeworks.size());
                    } catch (Exception e) {
                        userRepository.setErrorStatus(user.getId());
                        log.warn("Ошибка получения обновлений для пользователя с id={}", user.getId());
                    }
                });
    }
}
