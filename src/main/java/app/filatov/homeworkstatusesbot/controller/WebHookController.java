package app.filatov.homeworkstatusesbot.controller;

import app.filatov.homeworkstatusesbot.bot.HomeworkStatusesBot;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class WebHookController {
    HomeworkStatusesBot bot;

    public WebHookController(HomeworkStatusesBot bot) {
        this.bot = bot;
    }

    @PostMapping
    public BotApiMethod<?> update(@RequestBody Update update) {
        return bot.onWebhookUpdateReceived(update);
    }
}
