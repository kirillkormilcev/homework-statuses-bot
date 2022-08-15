package app.filatov.homeworkstatusesbot.bot;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HomeworkStatusesBot extends TelegramWebhookBot {
    @Autowired
    final MessageRouter handler;
    String botUsername;
    String botToken;
    String botPath;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotPath() {
        return botPath;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return handler.handleUpdate(update);
    }
}