package com.varshaa.aiassistant.command;

public class HelpCommand implements Command{
    @Override
    public boolean execute(String userInput) {
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
        return false;
    }
}
