package app.filatov.homeworkstatusesbot.bot.handle.texthandler;

import app.filatov.homeworkstatusesbot.bot.handle.texthandler.message.MessageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Постоянная клавиатура - главное меню для зарегестрированных пользователей
 */
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ReplyKeyboardMaker {

    final MessageService messageService;


    public ReplyKeyboardMarkup getHomeworkMenuKeyBoard(String language) {
        KeyboardRow row1 = new KeyboardRow();
        KeyboardButton checkUpdates = new KeyboardButton();
        checkUpdates.setText(messageService.getMessage("message.menu.buttons.check-updates",
                language));
        KeyboardButton getLastHomework = new KeyboardButton();
        getLastHomework.setText(messageService.getMessage("message.menu.buttons.get-last",
                language));
        row1.add(checkUpdates);
        row1.add(getLastHomework);

        KeyboardRow row2 = new KeyboardRow();
        KeyboardButton getAllHomeworks = new KeyboardButton();
        getAllHomeworks.setText(messageService.getMessage("message.menu.buttons.get-all",
                language));
        row2.add(getAllHomeworks);

        List<KeyboardRow> homeworkMenu = new ArrayList<>();
        homeworkMenu.add(row1);
        homeworkMenu.add(row2);
        homeworkMenu.add(makeSettingsAndHelpRow(language));

        return makeDefaultSettingsKeyboard(homeworkMenu);
    }

    public ReplyKeyboardMarkup getMainMenuKeyboard(String language) {
        List<KeyboardRow> mainMenu = new ArrayList<>();
        mainMenu.add(makeSettingsAndHelpRow(language));
        return makeDefaultSettingsKeyboard(mainMenu);
    }

    private KeyboardRow makeSettingsAndHelpRow(String language) {
        KeyboardRow row = new KeyboardRow();
        KeyboardButton help = new KeyboardButton();
        help.setText(messageService.getMessage("message.menu.buttons.help", language));

        KeyboardButton settings = new KeyboardButton();
        settings.setText(messageService.getMessage("message.menu.buttons.settings", language));
        row.add(help);
        row.add(settings);
        return row;
    }

    private ReplyKeyboardMarkup makeDefaultSettingsKeyboard(List<KeyboardRow> keyboard) {
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        return replyKeyboardMarkup;
    }
}
