package com.varshaa.aiassistant.command;

public class AboutCommand implements Command{
    @Override
    public boolean execute(String userInput) {
        if(userInput.equalsIgnoreCase("/about")) {

            System.out.println("""
            =====================================
                    AI CLI ASSISTANT
            =====================================

            Built using:
            - Java
            - Ollama
            - Local LLM Models

            Features:
            - Streaming AI responses
            - Conversation memory
            - Role switching
            - Model switching
            - Persistent chat history

            Developed by Varshaa

            =====================================
            """);

            return true;
        }
        return false;
    }

    @Override
    public String description() {
        return "/about - About this application";
    }

    @Override
    public String commandName() {
        return "/about";
    }
}
