package app.filatov.homeworkstatusesbot.bot.config;

import app.filatov.homeworkstatusesbot.bot.HomeworkStatusesBot;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Data
@Configuration
@ConfigurationProperties(prefix = "telegram")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramBotConfig {
    String botUsername;
    String botToken;
    String botPath;

    @Bean
    public HomeworkStatusesBot homeworkStatusesBot() {
        return HomeworkStatusesBot.builder()
                .botUsername(botUsername)
                .botToken(botToken)
                .botPath(botPath)
                .build();
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setCacheSeconds(3600);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
