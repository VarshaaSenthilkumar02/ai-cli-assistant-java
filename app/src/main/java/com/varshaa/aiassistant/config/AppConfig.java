package com.varshaa.aiassistant.config;

public class AppConfig {
    private String currentConfig = "llama3";
    private String currentRole = "teacher";

    public String getCurrentConfig() {
        return currentConfig;
    }

    public void setCurrentConfig(String model) {
        this.currentConfig = model;
    }

    public String getCurrentRole() {
        return currentRole;
    }

    public void setCurrentRole(String role) {
        this.currentRole = role;
    }
}
