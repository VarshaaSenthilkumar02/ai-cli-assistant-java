package com.varshaa.aiassistant.command;

import com.varshaa.aiassistant.config.AppConfig;

public class ModelCommand implements Command{
    private final AppConfig config;

    public ModelCommand(AppConfig config) {
        this.config = config;
    }

    @Override
    public boolean execute(String userInput) {
        if(userInput.startsWith("/model ")) {

            String modelName = userInput.replace("/model ", "").trim();
            config.setCurrentConfig(modelName);
            System.out.println("Model changed to : " + modelName);
            return true;
        }
        return false;
    }

    @Override
    public String description() {
        return "/model <name> - change AI model";
    }

    @Override
    public String commandName() {
        return "/model";
    }
}
