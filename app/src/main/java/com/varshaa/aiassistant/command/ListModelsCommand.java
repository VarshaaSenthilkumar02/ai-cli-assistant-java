package com.varshaa.aiassistant.command;

public class ListModelsCommand implements Command{
    @Override
    public boolean execute(String userInput) {
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
        return false;
    }
}
