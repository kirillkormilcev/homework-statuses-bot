package app.filatov.homeworkstatusesbot.bot.handle.language;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MessageLanguageSupplier implements LanguageSupplier {

    @Override
    public String getLanguage(Message message) {
        return message.getFrom().getLanguageCode();
    }
}
