package app.filatov.homeworkstatusesbot.bot.handle.message;

import app.filatov.homeworkstatusesbot.bot.handle.state.UserState;
import app.filatov.homeworkstatusesbot.bot.handle.util.HandlerUtil;
import app.filatov.homeworkstatusesbot.exception.UserNotFoundException;
import app.filatov.homeworkstatusesbot.model.User;
import app.filatov.homeworkstatusesbot.model.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@Component
public class RegistrationHandler implements MessageHandler {
    private final HandlerUtil util;

    private final UserRepository userRepository;

    public RegistrationHandler(HandlerUtil util, UserRepository userRepository) {
        this.util = util;
        this.userRepository = userRepository;
    }

    @Override
    public SendMessage handle(Message message) {
        Long chatId = message.getChatId();
        Optional<User> optional = userRepository.findById(message.getFrom().getId());
        if (optional.isEmpty()) {
            throw new UserNotFoundException("Пользователь не найден");
        }
        User user = optional.get();

        if (util.hasApiKey(user)) {
            util.setCorrectStateForUser(user);
            return new SendMessage(String.valueOf(chatId), "Токен уже был зарегистрирован");
        }

        if (user.getState() == UserState.REGISTRATION) {
            user.setState(UserState.ASK_API_KEY);
            userRepository.save(user);
        }

        if (user.getState() == UserState.ASK_API_KEY) {
            user.setState(UserState.CHECK_API_KEY);
            userRepository.save(user);
            return new SendMessage(String.valueOf(chatId), "Введите токен");
        }

        if (user.getState() == UserState.CHECK_API_KEY) {
            user.setApiKey(message.getText());
            user.setState(UserState.READY);
            userRepository.save(user);
        }

        return new SendMessage(String.valueOf(chatId), "Токен зарегистрирован");
    }

    @Override
    public UserState getHandlerType() {
        return UserState.REGISTRATION;
    }
}
