package com.varshaa.aiassistant.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.varshaa.aiassistant.role.Role;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.varshaa.aiassistant.role.RoleFactory.getRole;
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

        Role selectedRole = getRole(role);
        String systemPrompt = selectedRole.getPrompt();

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
}