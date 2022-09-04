package app.filatov.homeworkstatusesbot.bot.handle.language;

import app.filatov.homeworkstatusesbot.exception.UserNotFoundException;
import app.filatov.homeworkstatusesbot.model.Setting;
import app.filatov.homeworkstatusesbot.model.SettingCompositeKey;
import app.filatov.homeworkstatusesbot.model.User;
import app.filatov.homeworkstatusesbot.model.repository.SettingRepository;
import app.filatov.homeworkstatusesbot.model.repository.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@Component
@Primary
public class DBLanguageSupplier implements LanguageSupplier{
    UserRepository userRepository;
    SettingRepository settingRepository;
    public  DBLanguageSupplier(UserRepository userRepository, SettingRepository settingRepository){
        this.userRepository = userRepository;
        this.settingRepository = settingRepository;
    }
    @Override
    public String getLanguage(Message message) {
        Long id = message.getChatId();
        Optional<User> user= userRepository.findById(id);
        if (user.isPresent()){
        Optional<Setting> userSettings = settingRepository.findById(SettingCompositeKey.builder()
                        .user(user.get()).key("LANGUAGE_CODE").build());

            return userSettings.get().getValue();
        }
        else {
            throw new UserNotFoundException("Пользователь не найден");
        }
    }
}
