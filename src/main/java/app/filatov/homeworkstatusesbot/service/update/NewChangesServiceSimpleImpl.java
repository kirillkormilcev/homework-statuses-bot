package app.filatov.homeworkstatusesbot.service.update;

import app.filatov.homeworkstatusesbot.bot.HomeworkStatusesBot;
import app.filatov.homeworkstatusesbot.model.Homework;
import app.filatov.homeworkstatusesbot.model.repository.HomeworkRepository;
import app.filatov.homeworkstatusesbot.model.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class NewChangesServiceSimpleImpl implements NewChangesService{
    private final HomeworkRepository homeworkRepository;
    private final UserRepository userRepository;
    private final HomeworkStatusesBot bot;

    String botUsername = System.getenv("BOT_USERNAME");
    String botToken = System.getenv("BOT_TOKEN");
    String botPath = System.getenv("BOT_PATH");
    public NewChangesServiceSimpleImpl (HomeworkRepository homeworkRepository,
                                        UserRepository userRepository) {
        this.homeworkRepository = homeworkRepository;
        this.userRepository = userRepository;
        this.bot = HomeworkStatusesBot.builder()
                .botUsername(botUsername)
                .botToken(botToken)
                .botPath(botPath)
                .build();
    }

    @Override
    public void checkChangesInHomeworks(List<Homework> homeworks, long userId) {
        List<Homework> homeworksOld = homeworkRepository.findHomeworkByUserId(userId);
        //homeworks.get(0).setStatus("teststatus"); //todo для проверки, вкл-выкл эту строку
        List<Homework> newHomeworks = new ArrayList<>(homeworks);
        if (homeworksOld.size() < homeworks.size()) {
            log.info("Появилась новые домашние работы");
            newHomeworks.removeAll(homeworksOld);
            for (Homework homework: newHomeworks) {
                System.out.println(homework.toString());
            }
            notificationMsg(userId, "Появились новые домашние работы: ", newHomeworks);
            return;
        }
        if (!homeworksOld.equals(homeworks)) {
            log.info("Изменения в домашних работах");
            newHomeworks.removeAll(homeworksOld);
            for (Homework homework: newHomeworks) {
                System.out.println(homework.toString());
            }
            notificationMsg(userId, "Появились изменения в домашних работах: ", newHomeworks);
        }
    }

    private void notificationMsg(long userId, String msg, List<Homework> newHomeworks) {
        SendMessage message = new SendMessage();
        if (userRepository.findById(userId).isPresent()) {
            message.setChatId(userRepository.findById(userId).get().getChatId());
        } else {
            log.warn("Пользователь {} не найден.", userId);
        }
        message.setText(msg + "\n" + listToString(newHomeworks));
        message.disableWebPagePreview();
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String listToString(List<Homework> homeworks) {
        StringBuilder listToString = new StringBuilder();
        for (Homework homework: homeworks) {
            listToString.append(homework.toString());
        }
        return listToString.toString();
    }
}
