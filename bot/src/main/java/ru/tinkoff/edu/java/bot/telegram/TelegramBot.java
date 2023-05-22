package ru.tinkoff.edu.java.bot.telegram;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tinkoff.edu.java.bot.telegram.command.*;

@Slf4j
@Getter
public class TelegramBot extends TelegramLongPollingCommandBot {
    private final String BotUsername;
    private final String BotToken;

    @Getter
    private final NonCommand nonCommand;

    /**
     * @param commands команды кроме help, которая уже вшита по умолчанию
     * @param botUsername
     * @param botToken
     */
    public TelegramBot(IBotCommand[] commands, String botUsername, String botToken) {
        BotUsername = botUsername;
        BotToken = botToken;

        nonCommand = new NonCommand();
        registerAll(commands);
        HelpCommand helpCommand = new HelpCommand(this);
        register(helpCommand);
    }

    public TelegramBot(String botUsername, String botToken) {
        BotUsername = botUsername;
        BotToken = botToken;

        nonCommand = new NonCommand();
        register(new StartCommand());
        register(new TrackCommand());
        register(new UntrackCommand());
        register(new ListCommand());
        HelpCommand helpCommand = new HelpCommand(this);
        register(helpCommand);
    }

    @SneakyThrows
    @Override
    public void processNonCommandUpdate(Update update) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String userName = message.getFrom().getUserName();
        SendMessage answer = new SendMessage();
        String result = nonCommand.nonCommandExecute(chatId, userName, message.getText());
        answer.setText(result);
        answer.setChatId(chatId.toString());
        execute(answer);
    }

    @SneakyThrows
    public void sendMessage(Long chatId, String text) {
       SendMessage message = new SendMessage();
       message.setChatId(chatId.toString());
       message.setText(text);
       execute(message);
    }
}
