package app.filatov.homeworkstatusesbot.bot.handle.texthandler.message;

import app.filatov.homeworkstatusesbot.bot.handle.callbackhandler.KeyboardSupplier;
import app.filatov.homeworkstatusesbot.bot.handle.language.LanguageSupplier;
import app.filatov.homeworkstatusesbot.bot.handle.texthandler.state.UserState;
import app.filatov.homeworkstatusesbot.bot.handle.texthandler.util.HandlerUtil;
import app.filatov.homeworkstatusesbot.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class SettingsHandler implements MessageHandler {
    private final HandlerUtil util;
    private final UserRepository userRepository;
    private final KeyboardSupplier keyboardSupplier;
    private final LanguageSupplier languageSupplier;
    private final MessageService messageService;

    @Override
    public SendMessage handle(Message message) {
        Long chatId = message.getChatId();
        userRepository.findById(message.getFrom().getId()).ifPresent(util::setCorrectStateForUser);

        SendMessage sendMessage = new SendMessage(String.valueOf(chatId),
                messageService.getMessage("message.settings", languageSupplier.getLanguage(message)));
        sendMessage.setReplyMarkup(keyboardSupplier.getSettingsButtonsMarkup(languageSupplier.getLanguage(message)));
        return sendMessage;
    }

    @Override
    public UserState getHandlerType() {
        return UserState.SETTINGS;
    }
}
