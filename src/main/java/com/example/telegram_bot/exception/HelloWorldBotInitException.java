package com.example.telegram_bot.exception;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class HelloWorldBotInitException extends RuntimeException {
    public HelloWorldBotInitException(String message, TelegramApiException cause) {
        super(message, cause);
    }
}
