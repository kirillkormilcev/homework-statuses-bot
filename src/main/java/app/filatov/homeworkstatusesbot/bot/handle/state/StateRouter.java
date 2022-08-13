package app.filatov.homeworkstatusesbot.bot.handle.state;

import app.filatov.homeworkstatusesbot.bot.handle.message.MessageHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StateRouter {

    private final Map<BotState, MessageHandler> handlers = new HashMap<>();

    public StateRouter(List<MessageHandler> handlers) {
        handlers.forEach(handler -> this.handlers.put(handler.getHandlerType(), handler));
    }

    public SendMessage process(BotState state, Message message) {
        MessageHandler messageHandler = handlers.get(getHandlerFamily(state));
        return messageHandler.handle(message);
    }

    private BotState getHandlerFamily(BotState state) {
        return switch (state) {
            case REGISTRATION, ASK_API_KEY, CHECK_API_KEY -> BotState.REGISTRATION;
            case READY -> BotState.READY;
            case SETTINGS -> BotState.SETTINGS;
            case HELP -> BotState.HELP;
        };
    }
}
