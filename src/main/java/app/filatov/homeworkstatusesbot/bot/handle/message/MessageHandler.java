package app.filatov.homeworkstatusesbot.bot.handle.message;

import app.filatov.homeworkstatusesbot.bot.handle.state.BotState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageHandler {
    SendMessage handle(Message message);

    BotState getHandlerType();
}
