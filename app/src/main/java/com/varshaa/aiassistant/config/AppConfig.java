package com.varshaa.aiassistant.config;

public class AppConfig {
    private String currentConfig = "llama3";

    public String getCurrentConfig() {
        return currentConfig;
    }

    public void setCurrentConfig(String model) {
        this.currentConfig = model;
    }
}
