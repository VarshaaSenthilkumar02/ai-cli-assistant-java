package com.varshaa.aiassistant.command;

import com.varshaa.aiassistant.storage.ConversationManager;

public class SessionsCommand implements Command{
    private final ConversationManager conversationManager;

    public SessionsCommand(ConversationManager conversationManager) {
        this.conversationManager = conversationManager;
    }

    @Override
    public boolean execute(String userInput) {
        if(userInput.equalsIgnoreCase("/sessions")) {
            conversationManager.listSessions();
            return true;
        }
        return false;
    }
}
