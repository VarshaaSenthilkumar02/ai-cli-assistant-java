package com.varshaa.aiassistant.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.net.http.HttpRequest.BodyPublishers.ofString;
import static java.time.Duration.ofSeconds;

public class OllamaService {

    private final HttpClient client;
    private final ObjectMapper objectMapper;

    public OllamaService() {
        this.client = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public String getAIResponse(String prompt, String model, String role) throws Exception {

        String systemPrompt = buildSystemPrompt(role);

        String finalPrompt = systemPrompt + "\n" + prompt;

        String escapedPrompt = finalPrompt
                .replace("\"", "\\\"")
                .replace("\n", "\\n");

        String requestBody = """
                {
                    "model": "%s",
                    "prompt": "%s",
                    "stream": false
                }
                """.formatted(model, escapedPrompt);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:11434/api/generate"))
                .header("Content-Type", "application/json")
                .POST(ofString(requestBody))
                .timeout(ofSeconds(60))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode jsonNode = objectMapper.readTree(response.body());

        return jsonNode.get("response").asText();
    }

    private String buildSystemPrompt(String role) {
        if (role.equalsIgnoreCase("teacher")) {

            return """
                    You are an excellent teacher.
                    
                    Your behavior rules:
                    - explain concepts clearly
                    - simplify difficult topics
                    - use examples
                    - teach step-by-step
                    - assume the user is learning
                    
                    Applicable for ALL subjects:
                    programming, science, math, history,
                    life skills, communication, and general learning.
                    
                    Avoid overly advanced jargon unless needed.
                    """;
        }

        if (role.equalsIgnoreCase("interviewer")) {

            return """
                    You are a strict interviewer and evaluator.
                    
                    Your behavior rules:
                    - challenge the user
                    - ask follow-up questions
                    - test understanding
                    - avoid long lectures
                    - be concise and analytical
                    
                    For any topic:
                    - first evaluate user's understanding
                    - then ask deeper questions
                    - encourage critical thinking
                    
                    Do not behave like a friendly teacher.
                    """;
        }

        if (role.equalsIgnoreCase("mentor")) {

            return """
                    You are a supportive and practical mentor.
                    
                    Your behavior rules:
                    - guide patiently
                    - encourage learning
                    - give practical advice
                    - focus on improvement
                    - motivate the user realistically
                    
                    Applicable for:
                    career growth, programming,
                    studies, projects, productivity,
                    and personal development.
                    
                    Be supportive but honest.
                    """;
        }
        return "You are a helpful AI assistant.";
    }
}