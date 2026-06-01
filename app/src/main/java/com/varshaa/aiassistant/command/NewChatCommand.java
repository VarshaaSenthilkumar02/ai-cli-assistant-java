package com.varshaa.aiassistant.command;

import com.varshaa.aiassistant.storage.ConversationManager;

public class NewChatCommand implements Command{
    private final ConversationManager conversationManager;

    public NewChatCommand(ConversationManager conversationManager) {
        this.conversationManager = conversationManager;
    }

    @Override
    public boolean execute(String userInput) {
        if(userInput.equalsIgnoreCase("/new")) {
            conversationManager.clearSessionHistory();
            System.out.println("Started a new chat session.");
            return true;
        }
        return false;
    }

    @Override
    public String description() {
        return "/new - Start a fresh session";
    }

    @Override
    public String commandName() {
        return "/new";
    }

    @Override
    public boolean matches(String input) {
        return input.equalsIgnoreCase("/new");
    }
}
