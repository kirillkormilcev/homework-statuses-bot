package app.filatov.homeworkstatusesbot.bot.handle.texthandler.message;

import app.filatov.homeworkstatusesbot.bot.handle.language.LanguageSupplier;
import app.filatov.homeworkstatusesbot.bot.handle.texthandler.ReplyKeyboardMaker;
import app.filatov.homeworkstatusesbot.bot.handle.texthandler.state.UserState;
import app.filatov.homeworkstatusesbot.bot.handle.texthandler.util.HandlerUtil;
import app.filatov.homeworkstatusesbot.bot.handle.texthandler.util.JsonConverter;
import app.filatov.homeworkstatusesbot.dto.HomeworkDto;
import app.filatov.homeworkstatusesbot.model.Homework;
import app.filatov.homeworkstatusesbot.model.repository.HomeworkRepository;
import app.filatov.homeworkstatusesbot.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReadyHandler implements MessageHandler {
    private final HandlerUtil util;
    private final UserRepository userRepository;
    private final HomeworkRepository homeworkRepository;
    private final ModelMapper modelMapper;
    private final LanguageSupplier languageSupplier;
    private final ReplyKeyboardMaker replyKeyboardMaker;
    private final MessageService messageService;
    private final JsonConverter jsonConverter;

    @Override
    public SendMessage handle(Message message) {
        Long chatId = message.getChatId();
        Long userId = message.getFrom().getId();
        userRepository.findById(userId).ifPresent(util::setCorrectStateForUser);

        String language = languageSupplier.getLanguage(message);
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), "Готов к работе");

        sendMessage.setReplyMarkup(replyKeyboardMaker.getHomeworkMenuKeyBoard(language));
        if (message.getText().equals(messageService.getMessage("message.menu.buttons.check-updates",
                language))) {
            sendMessage.setText("Проверяю обновления... Placeholder");
        } else if (message.getText().equals(messageService.getMessage("message.menu.buttons.get-all",
                language))) {
            sendMessage.setText(jsonConverter.toJson(getAllHomeworksDto(userId), language));
        } else if (message.getText().equals(messageService.getMessage("message.menu.buttons.get-last",
                language))) {
            getLastHomeworkDto(userId).ifPresent(homeworkDto -> sendMessage.setText(jsonConverter.toJson(homeworkDto,
                    language)));
        }

        return sendMessage;
    }

    private List<HomeworkDto> getAllHomeworksDto(Long userId) {
        List<Homework> homeworks = homeworkRepository.findAllByUserId(userId);

        return homeworks.stream()
                        .map(homework -> modelMapper.map(homework, HomeworkDto.class))
                        .toList();
    }

    private Optional<HomeworkDto> getLastHomeworkDto(Long userId) {
        return homeworkRepository.findFirstByUserIdOrderByDateUpdatedDesc(userId)
                                 .map(homework -> modelMapper.map(homework, HomeworkDto.class));
    }

    @Override
    public UserState getHandlerType() {
        return UserState.READY;
    }
}
