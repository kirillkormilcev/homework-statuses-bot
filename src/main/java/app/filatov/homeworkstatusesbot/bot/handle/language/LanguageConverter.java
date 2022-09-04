package app.filatov.homeworkstatusesbot.bot.handle.language;

import org.springframework.stereotype.Service;

@Service
public class LanguageConverter {
    public String getLanguageCode(String value) {
        return switch (value) {
            case "ru" -> "ru_RU";
            default -> "en";
        };
    }
}
