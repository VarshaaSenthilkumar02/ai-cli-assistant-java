package com.varshaa.aiassistant.storage;

public class ConversationManager {

    private final StringBuilder conversationHistory;

    public ConversationManager() {
        this.conversationHistory = new StringBuilder();
    }

    public void addUserMessage(String message) {

        conversationHistory
                .append("User: ")
                .append(message)
                .append("\n");
    }

    public void addAIMessage(String message) {

        conversationHistory
                .append("AI: ")
                .append(message)
                .append("\n");
    }

    public String getConversationHistory() {
        return conversationHistory.toString();
    }

    public void clearHistory() {
        conversationHistory.setLength(0);
    }
}
