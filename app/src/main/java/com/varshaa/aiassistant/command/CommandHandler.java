package com.varshaa.aiassistant.command;

import com.varshaa.aiassistant.config.AppConfig;
import com.varshaa.aiassistant.service.OllamaService;
import com.varshaa.aiassistant.storage.ConversationManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandHandler {

    private final List<Command> commandList;
    private final Map<String, Command> commandRegistry;

    public CommandHandler(
            ConversationManager conversationManager,
            AppConfig config, OllamaService ollamaService) {

        this.commandList = new ArrayList<>();
        this.commandRegistry = new HashMap<>();

        commandList.add(new NewChatCommand(conversationManager));
        commandList.add(new StatusCommand(config));
        commandList.add(new HelpCommand(commandList));
        commandList.add(new ClearChatCommand(conversationManager));
        commandList.add(new HistoryCommand(conversationManager));
        commandList.add(new ListRolesCommand());
        commandList.add(new ListModelsCommand());
        commandList.add(new SessionsCommand(conversationManager));
        commandList.add(new AboutCommand());
        commandList.add(new SaveChatCommand(conversationManager, ollamaService, config));

        // Don't add these to registry yet
        commandList.add(new RoleCommand(config));
        commandList.add(new ModelCommand(config));
        commandList.add(new SummaryCommand(conversationManager, ollamaService, config));
        commandList.add(new SearchCommand(conversationManager));

        for (Command command : commandList) {

            if(command.commandName().equals("/role") ||
                    command.commandName().equals("/model") || command.commandName().equals("/search")) {
                continue;
            }

            commandRegistry.put(
                    command.commandName(),
                    command
            );
        }
    }

    public boolean handleCommand(String userInput) {

        Command command = commandRegistry.get(userInput);

        if(command != null) {
            return command.execute(userInput);
        }

        if(userInput.startsWith("/role ")) {

            for(Command cmd : commandList) {
                if(cmd.commandName().equals("/role")) {
                    return cmd.execute(userInput);
                }
            }
        }

        if(userInput.startsWith("/model ")) {

            for(Command cmd : commandList) {
                if(cmd.commandName().equals("/model")) {
                    return cmd.execute(userInput);
                }
            }
        }

        if(userInput.startsWith("/search ")) {

            for(Command cmd : commandList) {
                if(cmd.commandName().equals("/search")) {
                    return cmd.execute(userInput);
                }
            }
        }

        return false;
    }
}