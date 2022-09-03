package app.filatov.homeworkstatusesbot.model.repository;

import app.filatov.homeworkstatusesbot.model.Setting;
import app.filatov.homeworkstatusesbot.model.SettingCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingRepository extends JpaRepository<Setting, SettingCompositeKey> {
}
