package com.varshaa.aiassistant.role;

public class DefaultRole implements Role {
    @Override
    public String getPrompt() {
        return """
                You are a helpful AI assistant.

                Keep responses concise and useful.
                """;
    }
}
