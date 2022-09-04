package app.filatov.homeworkstatusesbot.bot;

import app.filatov.homeworkstatusesbot.bot.handle.state.StateRouter;
import app.filatov.homeworkstatusesbot.bot.handle.state.UserState;
import app.filatov.homeworkstatusesbot.model.Setting;
import app.filatov.homeworkstatusesbot.model.SettingCompositeKey;
import app.filatov.homeworkstatusesbot.model.User;
import app.filatov.homeworkstatusesbot.model.repository.SettingRepository;
import app.filatov.homeworkstatusesbot.model.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Log4j2
@Component
public class MessageRouter {
    private final UserRepository userRepository;
    private final StateRouter stateRouter;
    private final SettingRepository settingRepository;

    public MessageRouter(UserRepository userRepository,
                         StateRouter stateRouter,
                         SettingRepository settingRepository) {
        this.userRepository = userRepository;
        this.stateRouter = stateRouter;
        this.settingRepository = settingRepository;
    }

    public SendMessage handleUpdate(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            return handleTextMessage(message);
        }

        return null;
    }

    private SendMessage handleTextMessage(Message message) {
        String text = message.getText();
        Long id = message.getFrom().getId();
        log.info("Получено сообщение от пользователя с id={}: {}", id, text);

        User user = userRepository.findById(id).orElseGet(() -> {
                    User newUser = userRepository.save(User.builder()
                            .id(id)
                            .firstName(message.getFrom().getFirstName())
                            .lastName(message.getFrom().getLastName())
                            .userName(message.getFrom().getUserName())
                            .state(UserState.REGISTRATION)
                            .build());
                    // Сохраняем по умолчанию настройки для пользователя
                    settingRepository.save(Setting.builder().settingCompositeKey(SettingCompositeKey.builder()
                                    .user(newUser)
                                    .key("UPDATED")
                                    .build())
                            .value("true").build());

                    settingRepository.save(Setting.builder().settingCompositeKey(SettingCompositeKey.builder()
                                    .user(newUser)
                                    .key("LANGUAGE_CODE")
                                    .build())
                            .value(message.getFrom().getLanguageCode()).build());

                    return newUser;
                }
        );

        UserState state = switch (text) {
            case "/start" -> UserState.REGISTRATION;
            case "/settings" -> UserState.SETTINGS;
            case "/help" -> UserState.HELP;
            default -> user.getState();
        };

        user.setState(state);
        userRepository.save(user);

        return stateRouter.process(state, message);
    }
}
