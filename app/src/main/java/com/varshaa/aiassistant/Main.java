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

        System.out.print("Ask something: ");
        String userInput = scanner.nextLine();

        // JSON request body for Ollama
        String requestBody = """
        {
          "model": "llama3",
          "prompt": "%s",
          "stream": false
        }
        """.formatted(userInput);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:11434/api/generate"))
                .header("Content-Type", "application/json")
                .POST(ofString(requestBody))
                .build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

         ObjectMapper objectMapper = new ObjectMapper();

         JsonNode jsonNode = objectMapper.readTree(response.body());

         // Step 6: Extract only "response" field
         String aiResponse = jsonNode.get("response").asText();

         // Step 7: Print clean output
         System.out.println("\nAI:");
         System.out.println(aiResponse);

        scanner.close();
    }
}