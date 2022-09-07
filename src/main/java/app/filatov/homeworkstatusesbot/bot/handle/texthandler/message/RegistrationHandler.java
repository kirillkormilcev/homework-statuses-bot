package app.filatov.homeworkstatusesbot.bot.handle.texthandler.message;

import app.filatov.homeworkstatusesbot.bot.handle.language.LanguageSupplier;
import app.filatov.homeworkstatusesbot.bot.handle.texthandler.state.UserState;
import app.filatov.homeworkstatusesbot.bot.handle.texthandler.util.HandlerUtil;
import app.filatov.homeworkstatusesbot.exception.UserNotFoundException;
import app.filatov.homeworkstatusesbot.model.User;
import app.filatov.homeworkstatusesbot.model.repository.UserRepository;
import app.filatov.homeworkstatusesbot.service.loader.LoaderService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@Component
public class RegistrationHandler implements MessageHandler {
    private final HandlerUtil util;

    private final UserRepository userRepository;

    private final LoaderService loaderService;

    private final MessageService messageService;

    private final LanguageSupplier languageSupplier;

    public RegistrationHandler(HandlerUtil util,
                               UserRepository userRepository,
                               LoaderService loaderService, MessageService messageService, LanguageSupplier languageSupplier) {
        this.util = util;
        this.userRepository = userRepository;
        this.loaderService = loaderService;
        this.messageService = messageService;
        this.languageSupplier = languageSupplier;
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
            return new SendMessage(String.valueOf(chatId),
                    messageService.getMessage("message.registration.key.warning",
                            languageSupplier.getLanguage(message)));
        }

        if (user.getState() == UserState.REGISTRATION) {
            user.setState(UserState.ASK_API_KEY);
            userRepository.save(user);
        }

        if (user.getState() == UserState.ASK_API_KEY) {
            user.setState(UserState.CHECK_API_KEY);
            userRepository.save(user);
            return new SendMessage(String.valueOf(chatId),
                    messageService.getMessage("message.registration.key.enter",
                            languageSupplier.getLanguage(message)));
        }

        if (user.getState() == UserState.CHECK_API_KEY) {
            user.setApiKey(message.getText());
            try {
                loaderService.getHomeworks(user);
            } catch (Exception e) {
                user.setState(UserState.ASK_API_KEY);
                return new SendMessage(String.valueOf(chatId),
                        messageService.getMessage("message.registration.key.incorrect",
                        languageSupplier.getLanguage(message)));
            }
            user.setState(UserState.READY);
            userRepository.save(user);
        }

        return new SendMessage(String.valueOf(chatId),
                messageService.getMessage("message.registration.key.complete",
                languageSupplier.getLanguage(message)));
    }

    @Override
    public UserState getHandlerType() {
        return UserState.REGISTRATION;
    }
}
