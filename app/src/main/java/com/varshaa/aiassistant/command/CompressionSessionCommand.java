package com.varshaa.aiassistant.command;

import com.varshaa.aiassistant.storage.ConversationManager;

public class CompressionSessionCommand implements Command{

    private final ConversationManager conversationManager;

    public CompressionSessionCommand(ConversationManager conversationManager) {
        this.conversationManager = conversationManager;
    }

    @Override
    public boolean execute(String userInput) {
        conversationManager.compressSession();
        return true;
    }

    @Override
    public String description() {
        return "/compress - Compress Conversation testing";
    }

    @Override
    public String commandName() {
        return "/compress";
    }

    @Override
    public boolean matches(String input) {
        return input.equalsIgnoreCase("/compress");
    }
}
