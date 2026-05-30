package com.varshaa.aiassistant.command;

import com.varshaa.aiassistant.config.AppConfig;

public class StatusCommand implements Command {
    private final AppConfig config;

    public StatusCommand(AppConfig config) {
        this.config = config;
    }

    @Override
    public boolean execute(String userInput) {

        if(!userInput.equalsIgnoreCase("/status")) return false;
        System.out.println("""
        ====== AI Assistant Status ======

        Current Model : %s
        Current Role  : %s
    
        ================================
    """.formatted(config.getCurrentConfig(), config.getCurrentRole()));
        return true;
    }

    @Override
    public String description() {
        return "/status - Show current assistant configuration";
    }

    @Override
    public String commandName() {
        return "/status";
    }
}
