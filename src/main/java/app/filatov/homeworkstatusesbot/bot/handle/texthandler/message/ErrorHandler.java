package app.filatov.homeworkstatusesbot.bot.handle.texthandler.message;

import app.filatov.homeworkstatusesbot.bot.handle.callbackhandler.KeyboardSupplier;
import app.filatov.homeworkstatusesbot.bot.handle.language.LanguageSupplier;
import app.filatov.homeworkstatusesbot.bot.handle.texthandler.state.UserState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class ErrorHandler implements MessageHandler {
    private final MessageService messageService;
    private final LanguageSupplier languageSupplier;
    private final KeyboardSupplier keyboardSupplier;

    @Override
    public SendMessage handle(Message message) {
        String language = languageSupplier.getLanguage(message);
        SendMessage sendMessage = new SendMessage(String.valueOf(message.getChatId()),
                messageService.getMessage("message.error", language));
        sendMessage.setReplyMarkup(keyboardSupplier.recallApiKeyMarkup(language));
        return sendMessage;
    }

    @Override
    public UserState getHandlerType() {
        return UserState.ERROR;
    }
}
