package com.varshaa.aiassistant.storage;

import com.varshaa.aiassistant.config.AppConfig;
import com.varshaa.aiassistant.service.OllamaService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConversationManager {

    private final StringBuilder persistentHistory;
    private final ConversationStorage storage;
    private final StringBuilder sessionHistory;
    private final OllamaService ollamaService;
    private final AppConfig appConfig;
    private static final String MESSAGE_SEPARATOR = "\n---MESSAGE_SEPARATOR---\n";

    public ConversationManager(OllamaService ollamaService, AppConfig appConfig) {
        this.storage = new ConversationStorage();
        this.persistentHistory = new StringBuilder(storage.loadConversation());
        this.sessionHistory = new StringBuilder();
        this.ollamaService = ollamaService;
        this.appConfig = appConfig;
    }

    public void addToSession(String role, String message) {

        sessionHistory.append(role);
        sessionHistory.append(": ");
        sessionHistory.append(message);
        sessionHistory.append(MESSAGE_SEPARATOR);
    }

    public void compressSession() {

        String history = sessionHistory.toString();

        if(history.isBlank()) {
            System.out.println("No session history to compress.");
            return;
        }

        String[] messages = history.split(MESSAGE_SEPARATOR);

        List<String> sessionMessages =
                Arrays.stream(messages)
                        .filter(msg -> !msg.isBlank())
                        .toList();

        // Number of recent raw messages to keep
        int keepCount = 8; //later change this to pair count (user + AI response = 1)

        // Avoid compressing very small conversations
        if(sessionMessages.size() <= keepCount) {
            System.out.println("Not enough conversation to compress yet.");
            return;
        }

        // Get recent messages
        int start = Math.max(0, sessionMessages.size() - keepCount);

        List<String> recentMessages = sessionMessages.subList(start, sessionMessages.size());

        // Older messages to summarize
        List<String> oldMessages = sessionMessages.subList(0, start);

        // Convert old messages into text block
        String oldHistory = String.join(MESSAGE_SEPARATOR, oldMessages);

        String prompt = """
        You are generating long-term memory for an AI assistant.

        Return ONLY raw memory.

        Do NOT add introductory sentences.
        Do NOT explain the output.
        
        You MUST return ONLY bullet point memory.
                
        INVALID RESPONSE EXAMPLE:
        "Here are some points..."
                
        VALID RESPONSE EXAMPLE:
                
        Technical Concepts:
            - JVM
            - Heap
                

        Start directly with:

        Technical Concepts:
        - ...

        Features Implemented:
        - ...

        User Goals:
        - ...

        Important Decisions:
        - ...

        Preserve all important technical concepts.
        Do not summarize too aggressively.
        Do not speak conversationally.

        Conversation:
        %s
        """.formatted(oldHistory);

        try {

            String summary =
                    ollamaService.getAIResponse(
                            prompt,
                            appConfig.getCurrentConfig(),
                            appConfig.getCurrentRole()
                    );

            // Clear old session
            sessionHistory.setLength(0);

            // Add compressed summary
            sessionHistory.append("[SESSION SUMMARY]\n\n");
            sessionHistory.append(summary);
            sessionHistory.append(MESSAGE_SEPARATOR);

            // Add recent raw messages
            for(String recentMessage : recentMessages) {

                sessionHistory.append(recentMessage);
                sessionHistory.append(MESSAGE_SEPARATOR);
            }

            System.out.println("Session compressed successfully!");

            // Debugging output
            System.out.println(sessionHistory);

        } catch (Exception e) {

            System.out.println("Unable to compress session.");
            System.out.println(e.getMessage());
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
                .append(MESSAGE_SEPARATOR);
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
                .append(MESSAGE_SEPARATOR);
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

    public String getConversationForPrompt() {

        return sessionHistory
                .toString()
                .replace(MESSAGE_SEPARATOR, "\n");
    }

    private void save() {
        storage.saveConversation(persistentHistory.toString());
    }

    public void saveConversation(String fileName) {
        storage.exportConversation(sessionHistory.toString(), fileName);
    }

    public void listSessions() { storage.listConversations(); }
}