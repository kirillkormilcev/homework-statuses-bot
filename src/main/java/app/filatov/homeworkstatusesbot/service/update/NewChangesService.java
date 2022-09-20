package app.filatov.homeworkstatusesbot.service.update;

import app.filatov.homeworkstatusesbot.model.Homework;

import java.util.List;

public interface NewChangesService {
    void checkChangesInHomeworks(List<Homework> homeworks, long UserId);
}
