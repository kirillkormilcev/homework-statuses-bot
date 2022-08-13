package app.filatov.homeworkstatusesbot.bot.handle.util;

import app.filatov.homeworkstatusesbot.bot.handle.state.BotState;
import app.filatov.homeworkstatusesbot.model.User;
import app.filatov.homeworkstatusesbot.model.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class HandlerUtil {
    private final UserRepository userRepository;

    public HandlerUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setCorrectStateForUser(User user) {
        if (user.getApiKey() != null) {
            user.setState(BotState.READY);
        } else {
            user.setState(BotState.REGISTRATION);
        }
        userRepository.save(user);
    }

    public boolean hasApiKey(User user) {
        return user.getApiKey() != null;
    }
}
