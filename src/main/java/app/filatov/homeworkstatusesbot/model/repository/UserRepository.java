package app.filatov.homeworkstatusesbot.model.repository;

import app.filatov.homeworkstatusesbot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
