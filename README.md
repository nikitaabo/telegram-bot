# Telegram Bot in Java

A simple Telegram bot built with Java and Spring Boot, demonstrating the implementation of a menu with two nested levels. Link to the Telegram bot - @GarageRacerTABot

## Project Description

This project implements a test assignment to create a Telegram bot in Java. The bot features two menu levels that allow users to interact with it using buttons.

### Functionality

* The `/start` command brings up the main menu (Menu 1)
* Menu 1:
   * Button 1: when pressed, the bot sends a message "Button 1"
   * Button 2: when pressed, the bot sends a message "Button 2"
   * "Next" button: transitions to Menu 2
* Menu 2:
   * Button 1: when pressed, the bot sends a message "Button 1"
   * Button 2: when pressed, the bot sends a message "Button 2"
   * "Back" button: returns to Menu 1

## Technologies

* Java 8+
* Spring Boot
* Telegram Bot API (telegrambots-spring-boot-starter)
* Lombok for reducing boilerplate code

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           └── telegram_bot/
│   │               ├── TelegramBotApplication.java
│   │               ├── service/
│   │               │   └── HelloWorldBot.java
│   │               │   └── InitializerBot.java
│   │               └── exception/
│   │                   └── HelloWorldBotInitException.java
│   └── resources/
│       └── application.properties
```

## Running the Project

### Through an IDE

1. Open the project in your IDE (IntelliJ IDEA, Eclipse, etc.)
2. Run the `TelegramBotApplication` class
3. Use the bot in Telegram

### Using Maven

```
mvn spring-boot:run
```

## Implementation Features

* Uses Spring Boot for dependency management and configuration
* The bot implements the `TelegramLongPollingBot` interface
* A `Map<Long, String>` is used to store the menu state for each user
* Code is structured to minimize duplication (DRY principle)
* User actions are logged

## Author

Nikita Boiko (telegram - @nikitaa211).
