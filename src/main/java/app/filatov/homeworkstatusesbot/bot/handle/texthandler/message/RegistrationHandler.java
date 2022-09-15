package app.filatov.homeworkstatusesbot.bot.handle.texthandler.message;

import app.filatov.homeworkstatusesbot.bot.handle.language.LanguageSupplier;
import app.filatov.homeworkstatusesbot.bot.handle.texthandler.ReplyKeyboardMaker;
import app.filatov.homeworkstatusesbot.bot.handle.texthandler.state.UserState;
import app.filatov.homeworkstatusesbot.bot.handle.texthandler.util.HandlerUtil;
import app.filatov.homeworkstatusesbot.exception.UserNotFoundException;
import app.filatov.homeworkstatusesbot.model.User;
import app.filatov.homeworkstatusesbot.model.repository.UserRepository;
import app.filatov.homeworkstatusesbot.service.loader.LoaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RegistrationHandler implements MessageHandler {
    private final HandlerUtil util;

    private final UserRepository userRepository;
    private final LoaderService loaderService;
    private final MessageService messageService;
    private final LanguageSupplier languageSupplier;
    private final ReplyKeyboardMaker replyKeyboardMaker;

    @Override
    public SendMessage handle(Message message) {
        Long chatId = message.getChatId();
        Optional<User> optional = userRepository.findById(message.getFrom().getId());
        if (optional.isEmpty()) {
            throw new UserNotFoundException("Пользователь не найден");
        }
        User user = optional.get();

        String language = languageSupplier.getLanguage(message);
        if (util.hasApiKey(user)) {
            util.setCorrectStateForUser(user);

            SendMessage sendMessage = new SendMessage(String.valueOf(chatId),
                    messageService.getMessage("message.registration.key.warning",
                            language));
            sendMessage.setReplyMarkup(replyKeyboardMaker.getHomeworkMenuKeyBoard(language));
            return sendMessage;
        }

        if (user.getState() == UserState.REGISTRATION) {
            user.setState(UserState.ASK_API_KEY);
            userRepository.save(user);
        }

        if (user.getState() == UserState.ASK_API_KEY) {
            user.setState(UserState.CHECK_API_KEY);
            userRepository.save(user);
            SendMessage sendMessage = new SendMessage(String.valueOf(chatId),
                    messageService.getMessage("message.registration.key.enter",
                            language));
            sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard(language));
            return sendMessage;
        }

        if (user.getState() == UserState.CHECK_API_KEY) {
            user.setApiKey(message.getText());
            try {
                loaderService.getHomeworks(user);
            } catch (Exception e) {
                user.setState(UserState.ASK_API_KEY);
                SendMessage sendMessage = new SendMessage(String.valueOf(chatId),
                        messageService.getMessage("message.registration.key.incorrect",
                                language));
                sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard(language));
                return sendMessage;
            }
            user.setState(UserState.READY);
            userRepository.save(user);
        }

        SendMessage sendMessage = new SendMessage(String.valueOf(chatId),
                messageService.getMessage("message.registration.key.complete",
                        language));
        sendMessage.setReplyMarkup(replyKeyboardMaker.getHomeworkMenuKeyBoard(language));
        return sendMessage;
    }

    @Override
    public UserState getHandlerType() {
        return UserState.REGISTRATION;
    }
}
