package com.varshaa.aiassistant.command;

import com.varshaa.aiassistant.config.AppConfig;

public class CommandHandler {

    public boolean handleCommand(String userInput,
                                 StringBuilder conversationHistory, AppConfig config) {

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

        return false;
    }
}