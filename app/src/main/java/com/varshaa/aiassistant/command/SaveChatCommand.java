package com.varshaa.aiassistant.command;

import com.varshaa.aiassistant.storage.ConversationManager;

public class SaveChatCommand implements Command {

    private final ConversationManager conversationManager;

    public SaveChatCommand(ConversationManager conversationManager) {
        this.conversationManager = conversationManager;
    }

    @Override
    public boolean execute(String userInput) {
        if(userInput.equalsIgnoreCase("/save")) {
            conversationManager.saveConversation();
            System.out.println("Conversation saved successfully!");
            return true;
        }
        return false;
    }

    @Override
    public String description() {
        return "/save - Export conversation history";
    }

    @Override
    public String commandName() {
        return "/save";
    }

    @Override
    public boolean matches(String input) {
        return input.equalsIgnoreCase("/save");
    }
}
