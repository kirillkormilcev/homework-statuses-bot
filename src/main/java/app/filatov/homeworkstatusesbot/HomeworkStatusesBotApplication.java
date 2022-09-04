package app.filatov.homeworkstatusesbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class HomeworkStatusesBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(HomeworkStatusesBotApplication.class, args);
    }
}
