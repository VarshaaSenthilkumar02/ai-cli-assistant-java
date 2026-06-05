package com.varshaa.aiassistant.command;

import com.varshaa.aiassistant.storage.ConversationManager;

public class SearchCommand implements Command{

    private final ConversationManager conversationManager;
    public SearchCommand(ConversationManager conversationManager) {
        this.conversationManager = conversationManager;
    }

    @Override
    public boolean execute(String userInput) {

        String keyword = userInput
                .replace("/search ", "")
                .trim();

        String history = conversationManager.getCurrentConversationContext();

        if(history.isBlank()) {
            System.out.println("No conversation history found.");
            return true;
        }

        String[] lines = history.split("\n");
        boolean found = false;

        System.out.println("""
            
            Search Results:
            ----------------
            """);

        for(String line : lines) {
            if(line.toLowerCase()
                    .contains(keyword.toLowerCase())) {
                System.out.println(line);
                found = true;
            }
        }
        if(!found) {
            System.out.println("No matching conversation found.");
        }
        return true;
    }

    @Override
    public String description() {
        return "/search - Search keyword";
    }

    @Override
    public String commandName() {
        return "/search";
    }

    @Override
    public boolean matches(String input) {
        return input.startsWith("/search ");
    }
}
