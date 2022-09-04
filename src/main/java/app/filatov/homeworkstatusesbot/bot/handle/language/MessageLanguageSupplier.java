package app.filatov.homeworkstatusesbot.bot.handle.language;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MessageLanguageSupplier implements LanguageSupplier {
    private final LanguageConverter languageConverter;

    public MessageLanguageSupplier(LanguageConverter languageConverter) {
        this.languageConverter = languageConverter;
    }

    @Override
    public String getLanguage(Message message) {
        return languageConverter.getLanguageCode(message.getFrom().getLanguageCode());
    }
}
