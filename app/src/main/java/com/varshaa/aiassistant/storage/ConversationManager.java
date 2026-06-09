package com.varshaa.aiassistant.storage;

import com.varshaa.aiassistant.config.AppConfig;
import com.varshaa.aiassistant.service.OllamaService;

public class ConversationManager {

    private final StringBuilder persistentHistory;
    private final ConversationStorage storage;
    private final StringBuilder sessionHistory;
    private final OllamaService ollamaService;
    private final AppConfig appConfig;

    public ConversationManager(OllamaService ollamaService, AppConfig appConfig) {
        this.storage = new ConversationStorage();
        this.persistentHistory = new StringBuilder(storage.loadConversation());
        this.sessionHistory = new StringBuilder();
        this.ollamaService = ollamaService;
        this.appConfig = appConfig;
    }

    public void compressSession() {

        String history = sessionHistory.toString();

        if(history.isBlank()) {
            System.out.println("No session history to compress.");
            return;
        }

        String prompt = """
            You are generating long-term memory for an AI assistant.
            Do not summarize too aggressively.
            Preserve all important technical concepts.

            Extract and preserve ALL important information from the conversation.

            Return ONLY structured memory in this format:

            Technical Concepts:
            - ...

            Features Implemented:
            - ...
    
            User Goals:
            - ...

            Important Decisions:
            - ...

            Keep all important technical discussions.
            Do not omit concepts.
            Do not add conversational text.
            Do not summarize too aggressively.

            Conversation:
            %s
            """.formatted(history);

        try {

            String summary =
                    ollamaService.getAIResponse(
                            prompt,
                            appConfig.getCurrentConfig(),
                            appConfig.getCurrentRole()
                    );

            sessionHistory.setLength(0);
            sessionHistory.append("[SESSION SUMMARY]");
            sessionHistory.append(summary);
            System.out.println("Session compressed successfully!");
            System.out.println(sessionHistory);

        } catch (Exception e) {

            System.out.println("Unable to compress session.");

        }
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

    public void clearSessionHistory() {
        sessionHistory.setLength(0);
    }

    public String getCurrentConversationContext() {
        return sessionHistory.toString();
    }

    private void save() {
        storage.saveConversation(persistentHistory.toString());
    }

    public void saveConversation(String fileName) {
        storage.exportConversation(sessionHistory.toString(), fileName);
    }

    public void listSessions() { storage.listConversations(); }
}