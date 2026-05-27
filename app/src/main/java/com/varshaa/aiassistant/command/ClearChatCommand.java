package com.varshaa.aiassistant.command;


import com.varshaa.aiassistant.storage.ConversationManager;

public class ClearChatCommand implements  Command{

    private final ConversationManager conversationManager;

    public ClearChatCommand(ConversationManager conversationManager) {
        this.conversationManager = conversationManager;
    }
    @Override
    public boolean execute(String userInput) {
        if(userInput.equalsIgnoreCase("/clear")) {

            conversationManager.clearHistory();
            System.out.println("== Conversation History Cleared! ==");
            return true;
        }
        return false;
    }
}
