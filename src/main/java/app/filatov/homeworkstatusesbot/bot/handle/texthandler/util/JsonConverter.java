package app.filatov.homeworkstatusesbot.bot.handle.texthandler.util;

import app.filatov.homeworkstatusesbot.bot.handle.texthandler.message.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class JsonConverter {
    private final ObjectMapper objectMapper;
    private final MessageService messageService;

    public String toJson(Object obj, String language) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            log.warn("Ошибка преобразования в JSON - {}", ex.getMessage());
            return messageService.getMessage("message.jackson.error", language);
        }
    }
}
