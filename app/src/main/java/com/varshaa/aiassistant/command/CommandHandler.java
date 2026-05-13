package com.varshaa.aiassistant.command;

import com.varshaa.aiassistant.config.AppConfig;
import com.varshaa.aiassistant.storage.ConversationManager;

public class CommandHandler {

    public boolean handleCommand(String userInput,
                                 ConversationManager conversationManager, AppConfig config) {

        if(userInput.equalsIgnoreCase("/help")) {

            System.out.println("""
                    Available Commands:
                    /help    -> Show commands
                    /history -> Show chat history
                    /clear   -> Clear memory
                    /exit    -> Stop assistant
                    """);
            return true;
        }

        if(userInput.equalsIgnoreCase("/clear")) {

            conversationManager.clearHistory();
            System.out.println("== Conversation History Cleared! ==");
            return true;
        }

        if(userInput.equalsIgnoreCase("/history")) {

            System.out.println("== Conversation History ==");
            System.out.println(conversationManager.getConversationHistory());
            return true;
        }

        if(userInput.equalsIgnoreCase("/models")) {

            System.out.println("""
            Available Models:
            llama3
            mistral
            gemma
            phi3
            """);

            return true;
        }

        if(userInput.startsWith("/model ")) {

            String modelName = userInput.replace("/model ", "").trim();
            config.setCurrentConfig(modelName);
            System.out.println("Model changed to : " + modelName);
            return true;
        }

        if(userInput.startsWith("/role ")) {
            String role = userInput.replace("/role ", "").trim();
            config.setCurrentRole(role);
            conversationManager.clearHistory();
            System.out.println("Taking the role of : " + role);
            return true;
        }

        return false;
    }
}