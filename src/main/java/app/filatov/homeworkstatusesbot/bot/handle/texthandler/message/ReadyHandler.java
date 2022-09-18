package app.filatov.homeworkstatusesbot.bot.handle.texthandler.message;

import app.filatov.homeworkstatusesbot.bot.handle.language.LanguageSupplier;
import app.filatov.homeworkstatusesbot.bot.handle.texthandler.ReplyKeyboardMaker;
import app.filatov.homeworkstatusesbot.bot.handle.texthandler.state.UserState;
import app.filatov.homeworkstatusesbot.bot.handle.texthandler.util.HandlerUtil;
import app.filatov.homeworkstatusesbot.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class ReadyHandler implements MessageHandler {
    private final HandlerUtil util;
    private final UserRepository userRepository;
    private final LanguageSupplier languageSupplier;
    private final ReplyKeyboardMaker replyKeyboardMaker;
    private final MessageService messageService;


    @Override
    public SendMessage handle(Message message) {
        Long chatId = message.getChatId();
        userRepository.findById(message.getFrom().getId()).ifPresent(util::setCorrectStateForUser);

        String language = languageSupplier.getLanguage(message);
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), "Готов к работе");

        sendMessage.setReplyMarkup(replyKeyboardMaker.getHomeworkMenuKeyBoard(language));
        if (message.getText().equals(messageService.getMessage("message.menu.buttons.check-updates",
                language))) {
            sendMessage.setText("Проверяю обновления... Placeholder");
        }
        if (message.getText().equals(messageService.getMessage("message.menu.buttons.get-all",
                language))) {
            sendMessage.setText("Возвращаю все домашки... Placeholder");
        }
        if (message.getText().equals(messageService.getMessage("message.menu.buttons.get-last",
                language))) {
            sendMessage.setText("Возвращаю последнюю домашку... Placeholder");
        }

        return sendMessage;
    }

    @Override
    public UserState getHandlerType() {
        return UserState.READY;
    }
}
