package app.filatov.homeworkstatusesbot.bot.handle.language;

import app.filatov.homeworkstatusesbot.exception.UserNotFoundException;
import app.filatov.homeworkstatusesbot.model.User;
import app.filatov.homeworkstatusesbot.model.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@Component
public class DBLanguageSupplier implements LanguageSupplier{
    UserRepository userRepository;

    public  DBLanguageSupplier(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public String getLanguage(Message message) {
        Long id = message.getChatId();
        Optional<User> user= userRepository.findById(id);
        if (user.isPresent()){
            return user.get().getLanguageCode();
        }
        else {
            throw new UserNotFoundException("Пользователь не найден");
        }
    }
}
