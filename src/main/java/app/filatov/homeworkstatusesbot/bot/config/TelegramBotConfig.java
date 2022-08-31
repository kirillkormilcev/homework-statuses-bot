package app.filatov.homeworkstatusesbot.bot.config;

import app.filatov.homeworkstatusesbot.bot.HomeworkStatusesBot;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Data
@Log4j2
@Configuration
@EnableScheduling
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "telegram")
public class TelegramBotConfig implements SchedulingConfigurer {
    ThreadPoolTaskScheduler scheduler;
    String botUsername;
    String botToken;
    String botPath;

    // Выполнение задач по расписанию
    TelegramBotConfig() {
        scheduler = new ThreadPoolTaskScheduler();
        scheduler.setErrorHandler(error -> log.error(error.getMessage()));
        scheduler.setThreadNamePrefix("@scheduled-");
        scheduler.initialize();
    }

    @Bean
    public HomeworkStatusesBot homeworkStatusesBot() {
        return HomeworkStatusesBot.builder()
                .botUsername(botUsername)
                .botToken(botToken)
                .botPath(botPath)
                .build();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(scheduler);
    }
}
