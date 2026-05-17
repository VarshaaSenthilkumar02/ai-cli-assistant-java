package com.varshaa.aiassistant.storage;

public class ConversationManager {

    private final StringBuilder persistentHistory;
    private final ConversationStorage storage;
    private final StringBuilder sessionHistory;

    public ConversationManager() {
        this.storage = new ConversationStorage();
        this.persistentHistory = new StringBuilder(storage.loadConversation());
        this.sessionHistory = new StringBuilder();
    }

    public void addUserMessage(String message) {
        persistentHistory
                .append("User: ")
                .append(message)
                .append("\n");
        sessionHistory
                .append("User: ")
                .append(message)
                .append("\n");
        save();
    }

    public void addAIMessage(String message) {
        persistentHistory
                .append("AI: ")
                .append(message)
                .append("\n");
        sessionHistory
                .append("AI: ")
                .append(message)
                .append("\n");
        save();
    }

    public String getPersistentHistory() {
        return persistentHistory.toString();
    }

    public void clearHistory() {
        persistentHistory.setLength(0);
        sessionHistory.setLength(0);
        save();
    }

    private void save() {
        storage.saveConversation(persistentHistory.toString());
    }

    public void saveConversation() {
        storage.exportConversation(sessionHistory.toString());
    }
}