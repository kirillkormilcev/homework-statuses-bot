package app.filatov.homeworkstatusesbot.bot;

import app.filatov.homeworkstatusesbot.bot.handle.state.BotState;
import app.filatov.homeworkstatusesbot.bot.handle.state.StateRouter;
import app.filatov.homeworkstatusesbot.model.User;
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

    public MessageRouter(UserRepository userRepository, StateRouter stateRouter) {
        this.userRepository = userRepository;
        this.stateRouter = stateRouter;
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

        User user = userRepository.findById(id).orElseGet(() -> userRepository.save(User.builder()
                .id(id)
                .firstName(message.getFrom().getFirstName())
                .lastName(message.getFrom().getLastName())
                .userName(message.getFrom().getUserName())
                .state(BotState.REGISTRATION)
                .build())
        );

        BotState state = switch (text) {
            case "/start" -> BotState.REGISTRATION;
            case "/settings" -> BotState.SETTINGS;
            case "/help" -> BotState.HELP;
            default -> user.getState();
        };

        user.setState(state);
        userRepository.save(user);

        return stateRouter.process(state, message);
    }
}
