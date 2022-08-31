package app.filatov.homeworkstatusesbot.bot.handle.message;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageService {
    private final MessageSource messageSource;
    private final Locale locale;

    public MessageService(MessageSource messageSource, LocaleProperties localeProperties) {
        this.messageSource = messageSource;
        this.locale = Locale.forLanguageTag(localeProperties.getTag());
    }

    public String getMessage(String message) {
        return messageSource.getMessage(message, null, locale);
    }
}
