package app.filatov.homeworkstatusesbot.model.repository;

import app.filatov.homeworkstatusesbot.model.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HomeworkRepository extends JpaRepository<Homework, Long> {

    @Query("""
            SELECT h FROM Homework h
            WHERE h.user.id = :userId
            ORDER BY h.dateUpdated DESC
            """)
    List<Homework> findHomeworkByUserId(long userId);
}
