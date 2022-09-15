package app.filatov.homeworkstatusesbot.bot.handle.texthandler.message;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageSource messageSource;
    private final Locale locale;

    public String getMessage(String message, String languageCode) {
        if (Arrays.stream(Locale.getAvailableLocales())
                  .map(Locale::toLanguageTag)
                  .toList().contains(languageCode)) {
            String sourceMessage = messageSource.getMessage(message, null, new Locale.Builder()
                    .setLanguageTag(languageCode)
                    .build());
            return sourceMessage;
        } else return messageSource.getMessage(message, null, locale);
    }
}
