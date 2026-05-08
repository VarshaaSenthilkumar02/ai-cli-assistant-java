package com.varshaa.aiassistant;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import static java.net.http.HttpRequest.BodyPublishers.ofString;

public class Main {

     static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

         HttpClient client = HttpClient.newHttpClient();
         ObjectMapper objectMapper = new ObjectMapper();

         System.out.println("=== AI Assistant Started ===");
         System.out.println("Type 'exit' to stop.\n");

        while(true) {
            System.out.print("You : ");
            String userInput = scanner.nextLine();

            if(userInput.equalsIgnoreCase("exit")) {
                System.out.println("AI Assistance Stopped!");
                break;
            }

            if(userInput.trim().isEmpty()) {
                System.out.println("Please enter a valid input!!!");
                continue;
            }

            // JSON request body for Ollama
            String requestBody = """
            {
                "model": "llama3",
                "prompt": "%s",
                "stream": false
            }""".formatted(userInput);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:11434/api/generate"))
                    .header("Content-Type", "application/json")
                    .POST(ofString(requestBody))
                    .build();

            // Step 5: Send request
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode jsonNode = objectMapper.readTree(response.body());

            // Step 6: Extract only "response" field
            String aiResponse = jsonNode.get("response").asText();

            // Step 7: Print clean output
            System.out.println("\nAI:");
            System.out.println(aiResponse);
        }

        scanner.close();
    }
}