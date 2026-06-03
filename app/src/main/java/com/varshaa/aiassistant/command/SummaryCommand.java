package com.varshaa.aiassistant.command;

import com.varshaa.aiassistant.config.AppConfig;
import com.varshaa.aiassistant.service.OllamaService;
import com.varshaa.aiassistant.storage.ConversationManager;

public class SummaryCommand implements Command{

    private final ConversationManager conversationManager;
    private final OllamaService ollamaService;
    private final AppConfig appConfig;

    public SummaryCommand(ConversationManager conversationManager, OllamaService ollamaService, AppConfig appConfig) {
        this.conversationManager = conversationManager;
        this.ollamaService = ollamaService;
        this.appConfig = appConfig;
    }

    @Override
    public boolean execute(String userInput) {
        String history = conversationManager.getCurrentConversationContext();
        if(history.isBlank()) {
            System.out.println("""
                No active conversation found.

                Start chatting first, then use /summary.
            """);
            return true;
        }
        String prompt = """
                You are an AI assistant.
                Summarize the following conversation in concise bullet points.
                Focus on important topics and decisions.

            %s
            """.formatted(history);
        try {
            ollamaService.getAIResponse(
                    prompt,
                    appConfig.getCurrentConfig(),
                    appConfig.getCurrentRole()
            );
        } catch (Exception e) {
            System.out.println("Unable to Summarize your history");
            System.out.println(e);
        }
        return true;
    }

    @Override
    public String description() {
        return "/summary - Summarize the active conversation";
    }

    @Override
    public String commandName() {
        return "/summary";
    }

    @Override
    public boolean matches(String input) {
        return input.equalsIgnoreCase("/summary");
    }
}
