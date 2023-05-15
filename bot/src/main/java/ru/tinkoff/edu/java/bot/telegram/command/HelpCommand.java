package ru.tinkoff.edu.java.bot.telegram.command;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Slf4j
public class HelpCommand extends AbstractCommand {
    private final ICommandRegistry commandRegistry;

    public HelpCommand(ICommandRegistry commandRegistry) {
        this("help", "вывести окно с командами",  commandRegistry);
    }

    public HelpCommand(String identifier, String description, ICommandRegistry commandRegistry) {
        super(identifier, description);
        this.commandRegistry = commandRegistry;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        StringBuilder builder = new StringBuilder();
        builder.append("*Список поддерживаемых команд:*\n\n");
        for (IBotCommand botCommand : commandRegistry.getRegisteredCommands()) {
            builder
                    .append("/")
                    .append(botCommand.getCommandIdentifier())
                    .append(" -- ")
                    .append(botCommand.getDescription())
                    .append("\n\n");
        }
        sendAnswer(absSender, chat.getId(), builder.toString());
    }
}
