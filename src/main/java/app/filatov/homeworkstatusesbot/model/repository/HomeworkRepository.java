package app.filatov.homeworkstatusesbot.model.repository;

import app.filatov.homeworkstatusesbot.model.Homework;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeworkRepository extends JpaRepository<Homework, Long> {
}
