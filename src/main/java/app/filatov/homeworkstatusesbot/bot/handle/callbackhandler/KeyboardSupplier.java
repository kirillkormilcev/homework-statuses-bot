package app.filatov.homeworkstatusesbot.bot.handle.callbackhandler;

import app.filatov.homeworkstatusesbot.bot.handle.texthandler.message.MessageService;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
@Configuration
@ConfigurationProperties(prefix = "api")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KeyboardSupplier {
    String url;

    private final MessageService messageService;


    public InlineKeyboardMarkup getApiKeyLinkButtonMarkup(String language) {
        // Клавиатура с кнопкой-ссылкой на получение API-KEY
        InlineKeyboardMarkup settingsKeyboardMarkup = new InlineKeyboardMarkup();
        // Первый ряд кнопок
        List<InlineKeyboardButton> keyboard = new ArrayList<>();

        // Кнопка-ссылка
        InlineKeyboardButton apiKeyLinkButton = new InlineKeyboardButton();
        apiKeyLinkButton.setText(messageService.getMessage("message.help.button",language));
        apiKeyLinkButton.setCallbackData("GET_API_KEY");
        apiKeyLinkButton.setUrl(url);

        // Добавляем кнопку на клавиатуру
        keyboard.add(apiKeyLinkButton);
        settingsKeyboardMarkup.setKeyboard(List.of(keyboard));

        return settingsKeyboardMarkup;
    }

    public InlineKeyboardMarkup getSettingsButtonsMarkup(String language) {
        // Клавиатура настройки языка и подписки на обновления статуса проверки ДЗ
        InlineKeyboardMarkup settingsKeyboardMarkup = new InlineKeyboardMarkup();

        // Первый ряд кнопок
        List<InlineKeyboardButton> keyboardButtonsFirstRow = new ArrayList<>();
        // Второй ряд кнопок
        List<InlineKeyboardButton> keyboardButtonsSecondRow = new ArrayList<>();
        // Третий ряд кнопок
        List<InlineKeyboardButton> keyboardButtonsThirdRow = new ArrayList<>();

        // Установка русского языка для бота
        InlineKeyboardButton englishLanguageButton = new InlineKeyboardButton();
        englishLanguageButton.setText(messageService
                .getMessage("message.setting.buttons.russian",language));
        englishLanguageButton.setCallbackData("SET_RUSSIAN_LANGUAGE");

        // Установка английского языка для бота
        InlineKeyboardButton russianLanguageButton = new InlineKeyboardButton();
        russianLanguageButton.setText(messageService
                .getMessage("message.setting.buttons.english",language));
        russianLanguageButton.setCallbackData("SET_ENGLISH_LANGUAGE");

        // Добавляем кнопки настройки на первый ряд клавиатуры
        keyboardButtonsFirstRow.add(englishLanguageButton);
        keyboardButtonsFirstRow.add(russianLanguageButton);

        // Подписка на обновления
        InlineKeyboardButton enabledUpdateButton = new InlineKeyboardButton();
        enabledUpdateButton.setText(messageService
                .getMessage("message.setting.buttons.subscribe",language));
        enabledUpdateButton.setCallbackData("ENABLED_UPDATE");

        // Отписка от обновлений
        InlineKeyboardButton disableUpdateButton = new InlineKeyboardButton();
        disableUpdateButton.setText(messageService
                .getMessage("message.setting.buttons.unsubscribe",language));
        disableUpdateButton.setCallbackData("DISABLE_UPDATE");

        // Добавляем кнопки настройки на второй ряд клавиатуры
        keyboardButtonsSecondRow.add(enabledUpdateButton);
        keyboardButtonsSecondRow.add(disableUpdateButton);

        // Отзыв АPI-key
        InlineKeyboardButton recallApiKey = new InlineKeyboardButton();
        recallApiKey.setText(messageService
                .getMessage("message.setting.buttons.recallKey",language));
        recallApiKey.setCallbackData("RECALL_API_KEY");

        // Добавляем кнопку отзыва API-key на третий ряд клавиатуры
        keyboardButtonsThirdRow.add(recallApiKey);

        // Добавляем ряды кнопок на клавиатуру
        settingsKeyboardMarkup
                .setKeyboard(List.of(keyboardButtonsFirstRow, keyboardButtonsSecondRow,keyboardButtonsThirdRow));

        return settingsKeyboardMarkup;
    }

    public InlineKeyboardMarkup recallApiKeyMarkup(String language) {
        InlineKeyboardMarkup recallApiKeyMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton recallApiKeyButton = new InlineKeyboardButton();
        recallApiKeyButton.setText(messageService.getMessage("message.setting.buttons.recallKey", language));
        recallApiKeyButton.setCallbackData("RECALL_API_KEY");
        recallApiKeyMarkup.setKeyboard(List.of(List.of(recallApiKeyButton)));
        return recallApiKeyMarkup;
    }
}
