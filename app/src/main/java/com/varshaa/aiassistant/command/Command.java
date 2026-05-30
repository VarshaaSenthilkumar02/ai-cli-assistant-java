package com.varshaa.aiassistant.command;

public interface Command {
    boolean execute(String userInput);
    String description();
    String commandName();
}
