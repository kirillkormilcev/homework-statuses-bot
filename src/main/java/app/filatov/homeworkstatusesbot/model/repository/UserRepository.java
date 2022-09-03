package app.filatov.homeworkstatusesbot.model.repository;

import app.filatov.homeworkstatusesbot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying
    @Transactional
    @Query("update User set state = 'ERROR' where id = :id")
    void setErrorStatus(@Param("id") Long id);

    @Query("select user from User user where user.state = 'READY'")
    List<User> findReadyToUpdateUser();
}
