package com.example.telegram_bot.service;

import com.example.telegram_bot.exception.HelloWorldBotInitException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Slf4j
public class HelloWorldBot extends TelegramLongPollingBot {
    private static final String MENU_1 = "Меню 1";
    private static final String MENU_2 = "Меню 2";
    private static final String START_COMMAND = "/start";
    private static final String BUTTON_1 = "Кнопка 1";
    private static final String BUTTON_2 = "Кнопка 2";
    private static final String NEXT = "Далі";
    private static final String BACK = "Назад";
    private final String botToken;
    private final String botUserName;
    private final Map<Long, String> userMenuState = new HashMap<>();

    public HelloWorldBot(@Value("${telegram.bot.token}") String botToken,
                         @Value("${telegram.bot.username}") String botUserName) {
        this.botToken = botToken;
        this.botUserName = botUserName;
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            try {
                switch (messageText) {
                    case START_COMMAND:
                        sendOnStartCommand(update.getMessage().getChat().getUserName()
                                , chatId);
                        sendMenu(chatId, MENU_1);
                        userMenuState.put(chatId, MENU_1);
                        log.info("Користувач {} перейшов на меню 1",
                                update.getMessage().getChat().getUserName());
                        break;
                    case BUTTON_1:
                        sendTextMessage(chatId, BUTTON_1);
                        log.info("Користувач {} пнатиснув на кнопку 1",
                                update.getMessage().getChat().getUserName());

                        break;
                    case BUTTON_2:
                        sendTextMessage(chatId, BUTTON_2);
                        log.info("Користувач {} пнатиснув на кнопку 2",
                                update.getMessage().getChat().getUserName());
                        break;
                    case NEXT:
                        sendMenu(chatId, MENU_2);
                        userMenuState.put(chatId, MENU_2);
                        log.info("Користувач {} перейшов на меню 2",
                                update.getMessage().getChat().getUserName());
                        break;
                    case BACK:
                        sendMenu(chatId, MENU_1);
                        userMenuState.put(chatId, MENU_1);
                        log.info("Користувач {} перейшов на меню 1",
                                update.getMessage().getChat().getUserName());
                        break;
                    default:
                        sendTextMessage(chatId, "Невідома команда. "
                                + "Використайте /start для відображення меню.");
                        log.info("Користувач {} ввів невідому команду",
                                update.getMessage().getChat().getUserName());
                }
            } catch (TelegramApiException e) {
                throw new HelloWorldBotInitException("There was an error during "
                        + "bot initialization", e);
            }
        }
    }

    private void sendOnStartCommand(String userName, long chatId) throws TelegramApiException {
        String message = """
                Вітаю, %s! 👋🏻 !
                                 
                Даний бот є результатом виконання тестового завдання на позицію Junior Java Developer
                у компанію Garage Racer 🏎️
                Тут ти можеш:
                📌 Переключатися між меню
                📌 Перевірити роботи кнопок                                     \s
                Дякую за увагу!
                """.formatted(userName);
        log.info("Sending a message, message: {}.", message);
        sendTextMessage(chatId, message);
    }

    private void sendTextMessage(long chatId, String text) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        if (userMenuState.containsKey(chatId)) {
            String menuState = userMenuState.get(chatId);
            if (MENU_1.equals(menuState)) {
                message.setReplyMarkup(getMenuKeyboard(menuState));
            } else if (MENU_2.equals(menuState)) {
                message.setReplyMarkup(getMenuKeyboard(menuState));
            }
        }

        execute(message);
    }

    private void sendMenu(long chatId, String menuName) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(menuName);
        message.setReplyMarkup(getMenuKeyboard(menuName));
        execute(message);
    }

    private ReplyKeyboardMarkup getMenuKeyboard(String menuName) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(BUTTON_1));
        row1.add(new KeyboardButton(BUTTON_2));

        KeyboardRow row2 = new KeyboardRow();
        if (MENU_1.equals(menuName)) {
            row2.add(new KeyboardButton(NEXT));
        } else if (MENU_2.equals(menuName)) {
            row2.add(new KeyboardButton(BACK));
        }
        keyboard.add(row1);
        keyboard.add(row2);

        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        return keyboardMarkup;
    }
}
