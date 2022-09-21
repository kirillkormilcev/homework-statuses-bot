package app.filatov.homeworkstatusesbot.service.update;

import app.filatov.homeworkstatusesbot.bot.HomeworkStatusesBot;
import app.filatov.homeworkstatusesbot.dto.HomeworkDto;
import app.filatov.homeworkstatusesbot.exception.UserNotFoundException;
import app.filatov.homeworkstatusesbot.model.Homework;
import app.filatov.homeworkstatusesbot.model.repository.HomeworkRepository;
import app.filatov.homeworkstatusesbot.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class NewChangesServiceSimpleImpl implements NewChangesService {
    private final HomeworkRepository homeworkRepository;
    private final UserRepository userRepository;
    private final HomeworkStatusesBot bot;
    private final ModelMapper modelMapper;

    @Override
    public void checkChangesInHomeworks(List<Homework> homeworks, long userId) {
        List<Homework> homeworksOld = homeworkRepository.findAllByUserIdOrderByDateUpdatedDesc(userId);
        List<Homework> newHomeworks = new ArrayList<>(homeworks);
        if (homeworksOld.size() < homeworks.size()) {
            log.info("Появилась новые домашние работы");
            newHomeworks.removeAll(homeworksOld);
            notificationMsg(userId, "Появились новые домашние работы (" + newHomeworks.size() + " шт): ", newHomeworks);
            return;
        }
        if (!homeworksOld.equals(homeworks)) {
            log.info("Изменения в домашних работах");
            newHomeworks.removeAll(homeworksOld);
            notificationMsg(userId, "Появились изменения в домашних работах: ", newHomeworks);
        }
    }

    private void notificationMsg(long userId, String msg, List<Homework> newHomeworks) {
        SendMessage message = new SendMessage();
        message.setChatId(userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("Пользователь " + userId + " не найден.")).getChatId());
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
        List<HomeworkDto> homeworksDto = homeworks.stream()
                .map(homework -> modelMapper.map(homework, HomeworkDto.class))
                .toList();
        for (HomeworkDto homeworkDto : homeworksDto) {
            listToString.append(homeworkDto.toString()).append("\n");
        }
        return listToString.toString();
    }
}
