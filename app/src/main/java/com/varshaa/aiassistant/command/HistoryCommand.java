package com.varshaa.aiassistant.command;

import com.varshaa.aiassistant.storage.ConversationManager;

public class HistoryCommand implements Command{

    private final ConversationManager conversationManager;

    public HistoryCommand(ConversationManager conversationManager) {
        this.conversationManager = conversationManager;
    }

    @Override
    public boolean execute(String userInput) {
        if(userInput.equalsIgnoreCase("/history")) {

            System.out.println("== Conversation History ==");
            System.out.println(conversationManager.getPersistentHistory());
            return true;
        }
        return false;
    }

    @Override
    public String description() {
        return "/history - Show conversation history";
    }

    @Override
    public String commandName() {
        return "/history";
    }

    @Override
    public boolean matches(String input) {
        return input.equalsIgnoreCase("/history");
    }
}
