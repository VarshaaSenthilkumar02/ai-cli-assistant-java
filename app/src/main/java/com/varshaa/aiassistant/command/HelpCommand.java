package com.varshaa.aiassistant.command;

import java.util.List;

public class HelpCommand implements Command{
    private final List<Command> commands;

    public HelpCommand(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public boolean execute(String userInput) {

        if(!userInput.equalsIgnoreCase("/help")) {
            return false;
        }

        System.out.println("""
    =====================================
            AVAILABLE COMMANDS
    =====================================
    """);

        for(Command command : commands) {
            System.out.println(command.description());
        }

        System.out.println("""
    
    =====================================
    """);

        return true;
    }

    @Override
    public String description() {
        return "/help - Show all commands";
    }

    @Override
    public String commandName() {
        return "/help";
    }

    @Override
    public boolean matches(String input) {
        return input.equalsIgnoreCase("/help");
    }
}
