package app.filatov.homeworkstatusesbot.bot.handle.language;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface LanguageSupplier {
    String getLanguage(Message message);
}
