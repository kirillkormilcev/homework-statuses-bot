package app.filatov.homeworkstatusesbot.model.repository;

import app.filatov.homeworkstatusesbot.model.Homework;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HomeworkRepository extends JpaRepository<Homework, Long> {
    List<Homework> findAllByUserId(Long userId);
    Optional<Homework> findFirstByUserIdOrderByDateUpdatedDesc(Long userId);

    List<Homework> findAllByUserIdOrderByDateUpdatedDesc(long userId);
}
