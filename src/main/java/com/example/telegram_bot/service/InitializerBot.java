package com.example.telegram_bot.service;

import com.example.telegram_bot.exception.HelloWorldBotInitException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
@RequiredArgsConstructor
public class InitializerBot {
    private final HelloWorldBot helloWorldBot;

    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(helloWorldBot);
        } catch (TelegramApiException e) {
            throw new HelloWorldBotInitException("There was an error during "
                    + "bot initialization", e);
        }
    }
}
