package com.varshaa.aiassistant.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.net.http.HttpRequest.BodyPublishers.ofString;

public class OllamaService {

    private final HttpClient client;
    private final ObjectMapper objectMapper;

    public OllamaService() {
        this.client = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public String getAIResponse(String prompt) throws Exception {

        String escapedPrompt = prompt
                .replace("\"", "\\\"")
                .replace("\n", "\\n");

        String requestBody = """
                {
                    "model": "llama3",
                    "prompt": "%s",
                    "stream": false
                }
                """.formatted(escapedPrompt);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:11434/api/generate"))
                .header("Content-Type", "application/json")
                .POST(ofString(requestBody))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode jsonNode = objectMapper.readTree(response.body());

        return jsonNode.get("response").asText();
    }
}