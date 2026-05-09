package com.varshaa.aiassistant;

import com.varshaa.aiassistant.service.OllamaService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class AiAssistance {

     static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

         System.out.println("=== AI Assistant Started ===");
         System.out.println("Type 'exit' to stop.\n");

         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
         StringBuilder conversationHistory = new StringBuilder();

         OllamaService aiModel = new OllamaService();

        while(true) {

            LocalDateTime currentTime = LocalDateTime.now();
            String time = currentTime.format(formatter).toUpperCase();

            System.out.print("[" + time + "] You : ");
            String userInput = scanner.nextLine();

            if(userInput.equalsIgnoreCase("/exit")) {
                System.out.println("AI Assistance Stopped!");
                break;
            }

            if(userInput.trim().isEmpty()) {
                System.out.println("Please enter a valid input!!!");
                continue;
            }

            if (userInput.equalsIgnoreCase("/help")) {
                System.out.println("""
                        Available Commands:
                        /help    -> Show commands
                        /history -> Show chat history
                        /clear   -> Clear memory
                        /exit    -> Stop assistant
                        """);
                continue;
            }

            if(userInput.equalsIgnoreCase("/clear")) {
                conversationHistory.setLength(0);
                System.out.println("== Conversation History Cleared! ==");
                continue;
            }

            if(userInput.equalsIgnoreCase("/history")) {
                System.out.println("== Conversation History: ==");
                System.out.println(conversationHistory);
                continue;
            }

            conversationHistory.append("User: ")
                    .append(userInput)
                    .append("\n");

            try {
                // Step 6: Extract only "response" field
                String aiResponse = aiModel.getAIResponse(conversationHistory.toString());
                conversationHistory.append("AI Response: ")
                        .append(aiResponse)
                        .append("\n");

                // Step 7: Print clean output
                System.out.println("\n[" + time + "]AI : ");
                System.out.println(aiResponse);
            } catch (Exception e) {
                System.out.println("Unable to Connect!");
                System.out.println(e);
            }
        }

        scanner.close();
    }
}