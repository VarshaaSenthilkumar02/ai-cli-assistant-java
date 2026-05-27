package com.varshaa.aiassistant.command;

import com.varshaa.aiassistant.config.AppConfig;
import com.varshaa.aiassistant.storage.ConversationManager;

import java.util.List;

public class CommandHandler {

    private final List<Command> commandList;

    public CommandHandler(ConversationManager conversationManager, AppConfig config) {
        this.commandList = List.of(new NewChatCommand(conversationManager),
                new StatusCommand(config),
                new SaveChatCommand(conversationManager),
                new HelpCommand(),
                new ClearChatCommand(conversationManager),
                new HistoryCommand(conversationManager),
                new ListModelsCommand(),
                new ModelCommand(config),
                new ListRolesCommand(),
                new SessionsCommand(conversationManager),
                new RoleCommand(config));
    }

    public boolean handleCommand(String userInput) {

        for(Command command : commandList) {
            if(command.execute(userInput)) return true;
        }
        return false;
    }
}