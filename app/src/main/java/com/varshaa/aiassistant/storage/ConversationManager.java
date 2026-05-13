package com.varshaa.aiassistant.storage;

public class ConversationManager {

    private final StringBuilder conversationHistory;
    private final ConversationStorage storage;

    public ConversationManager() {
        this.storage = new ConversationStorage();
        this.conversationHistory = new StringBuilder(storage.loadConversation());
    }

    public void addUserMessage(String message) {
        conversationHistory
                .append("User: ")
                .append(message)
                .append("\n");
        save();
    }

    public void addAIMessage(String message) {
        conversationHistory
                .append("AI: ")
                .append(message)
                .append("\n");
        save();
    }

    public String getConversationHistory() {
        return conversationHistory.toString();
    }

    public void clearHistory() {
        conversationHistory.setLength(0);
        save();
    }

    private void save() {
        storage.saveConversation(conversationHistory.toString());
    }
}