package com.varshaa.aiassistant.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
        String line;

        String escapedPrompt = finalPrompt
                .replace("\"", "\\\"")
                .replace("\n", "\\n");

        String requestBody = """
                {
                    "model": "%s",
                    "prompt": "%s",
                    "stream": true
                }
                """.formatted(model, escapedPrompt);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:11434/api/generate"))
                .header("Content-Type", "application/json")
                .POST(ofString(requestBody))
                .timeout(ofSeconds(60))
                .build();

        System.out.println("Generating Response.....");

        HttpResponse<InputStream> response =
                client.send(request, HttpResponse.BodyHandlers.ofInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.body()));

        StringBuilder fullResponse = new StringBuilder();

        while((line = reader.readLine()) != null) {
            JsonNode jsonNode = objectMapper.readTree(line);
            if(jsonNode.get("response") != null) {
                String token = jsonNode.get("response").asText();
                System.out.print(token);
                Thread.sleep(20);
                fullResponse.append(token);
            }
        }

        System.out.println();
        return fullResponse.toString();
    }

    private String buildSystemPrompt(String role) {
        if (role.equalsIgnoreCase("teacher")) {

            return """
                    You are a helpful teacher.
                    Rules:
                        - Keep answers short for simple questions.
                        - Give detailed explanations only when asked.
                        - Avoid unnecessary introductions.
                        - Be conversational and natural.
                        - Use examples only when needed.
                        - For greetings or personal messages, reply casually in 1-2 lines.
                        - Explain step-by-step only for learning questions.
                    
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