package app.filatov.homeworkstatusesbot.bot.handle.message;

import app.filatov.homeworkstatusesbot.bot.handle.state.UserState;
import app.filatov.homeworkstatusesbot.bot.handle.util.HandlerUtil;
import app.filatov.homeworkstatusesbot.model.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class HelpHandler implements MessageHandler {

    private final HandlerUtil util;
    private final UserRepository userRepository;
    private final MessageService messageService;

    public HelpHandler(HandlerUtil util, UserRepository userRepository, MessageService messageService) {
        this.util = util;
        this.userRepository = userRepository;
        this.messageService = messageService;
    }

    @Override
    public SendMessage handle(Message message) {
        Long chatId = message.getChatId();
        userRepository.findById(message.getFrom().getId()).ifPresent(util::setCorrectStateForUser);

        return new SendMessage(String.valueOf(chatId), messageService.getMessage("message.help"));
    }

    @Override
    public UserState getHandlerType() {
        return UserState.HELP;
    }
}
