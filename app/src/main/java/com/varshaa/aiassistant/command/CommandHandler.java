package com.varshaa.aiassistant.command;

import com.varshaa.aiassistant.config.AppConfig;
import com.varshaa.aiassistant.storage.ConversationManager;

public class CommandHandler {

    public boolean handleCommand(String userInput,
                                 ConversationManager conversationManager, AppConfig config) {

        if(userInput.equalsIgnoreCase("/help")) {

            System.out.println("""
            =====================================
                    AVAILABLE COMMANDS
            =====================================

            /help
                Show all commands

            /history
                Show conversation history

            /clear
                Clear conversation memory

            /models
                Show available AI models

            /model <name>
                Change current AI model

            /role <name>
                Change AI personality

            /about
                About this application

            /exit
                Stop assistant

            /save
                Export Conversation History
            
            /new
                Start a fresh session
            
            /staus
                show current assistant configuration
            
            /sessions
                Show saved chat sessions

            =====================================
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
            System.out.println(conversationManager.getPersistentHistory());
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

            if(!config.isValidRole(role)) {
                System.out.println("""
                Invalid role!

                Use /listroles to see available roles.""");

                return true;
            }

            config.setCurrentRole(role);
            System.out.println("Taking the role of : " + role);

            return true;
        }

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

        if(userInput.equalsIgnoreCase("/status")) {

            System.out.println("""
        ====== AI Assistant Status ======

        Current Model : %s
        Current Role  : %s

        ================================
        """.formatted(config.getCurrentConfig(), config.getCurrentRole()));

            return true;
        }

        if(userInput.equalsIgnoreCase("/save")) {

            conversationManager.saveConversation();
            System.out.println("Conversation saved successfully!");
            return true;
        }

        if(userInput.equalsIgnoreCase("/new")) {
            Command command = new NewChatCommand(conversationManager);
            return command.execute(userInput);
        }

        if(userInput.equalsIgnoreCase("/sessions")) {
            conversationManager.listSessions();
            return true;
        }

        if(userInput.equalsIgnoreCase("/listroles")) {
            System.out.println("""
            Available Roles:

            teacher
            mentor
            interviewer
            default
            """);
            return true;
        }

        return false;
    }
}