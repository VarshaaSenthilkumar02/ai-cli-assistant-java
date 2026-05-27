package com.varshaa.aiassistant.command;

import com.varshaa.aiassistant.config.AppConfig;

public class RoleCommand implements Command{
    private final AppConfig config;

    public RoleCommand(AppConfig config) {
        this.config = config;
    }

    @Override
    public boolean execute(String userInput) {
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
        return false;
    }
}
