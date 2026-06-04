package com.varshaa.aiassistant.command;

import com.varshaa.aiassistant.config.AppConfig;
import com.varshaa.aiassistant.service.OllamaService;
import com.varshaa.aiassistant.storage.ConversationManager;

public class SaveChatCommand implements Command {

    private final ConversationManager conversationManager;
    private final OllamaService ollamaService;
    private final AppConfig config;

    public SaveChatCommand(ConversationManager conversationManager, OllamaService ollamaService, AppConfig config) {
        this.conversationManager = conversationManager;
        this.ollamaService = ollamaService;
        this.config = config;
    }

    @Override
    public boolean execute(String userInput) {

        String history = conversationManager.getCurrentConversationContext();

        if(history.isBlank()) {

            System.out.println("No active conversation to save.");
            return true;
        }

        String prompt = """
            Generate a short title for this conversation.

            Rules:
            - Maximum 5 words
            - No special characters
            - Short and meaningful

            Conversation:
            %s
            """.formatted(history);

        try {
            String title = ollamaService.getAIResponse(
                    prompt,
                    config.getCurrentConfig(),
                    config.getCurrentRole()
            );
            title = title.split("\n")[0];
            title = title.replaceAll("[^a-zA-Z0-9\\s]", "");
            title = title.replaceAll("\\s+", "-");
            conversationManager.saveConversation(title);
            System.out.println("Conversation saved successfully!");
        } catch (Exception e) {
            System.out.println("Unable to save conversation");
            System.out.println(e.getMessage());
        }
        return true;
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
