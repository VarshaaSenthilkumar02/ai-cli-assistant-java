package com.varshaa.aiassistant.command;

public class ListRolesCommand implements Command{
    @Override
    public boolean execute(String userInput) {
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

    @Override
    public String description() {
        return "/listaRoles - List all the available roles";
    }

    @Override
    public String commandName() {
        return "/listroles";
    }

    @Override
    public boolean matches(String input) {
        return input.equalsIgnoreCase("/role");
    }
}
