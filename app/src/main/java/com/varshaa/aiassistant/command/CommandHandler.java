package com.varshaa.aiassistant.command;

import com.varshaa.aiassistant.config.AppConfig;
import com.varshaa.aiassistant.storage.ConversationStorage;

public class CommandHandler {

    public boolean handleCommand(String userInput,
                                 StringBuilder conversationHistory, AppConfig config, ConversationStorage storage) {

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

            conversationHistory.setLength(0);
            storage.saveConversation(conversationHistory.toString());
            System.out.println("== Conversation History Cleared! ==");
            return true;
        }

        if(userInput.equalsIgnoreCase("/history")) {

            System.out.println("== Conversation History ==");
            System.out.println(conversationHistory);

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
            conversationHistory.setLength(0);
            System.out.println("Taking the role of : " + role);
            return true;
        }

        return false;
    }
}