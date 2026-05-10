package com.varshaa.aiassistant;

import com.varshaa.aiassistant.command.CommandHandler;
import com.varshaa.aiassistant.config.AppConfig;
import com.varshaa.aiassistant.service.OllamaService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class AiAssistance {

     public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

         System.out.println("=== AI Assistant Started ===");
         System.out.println("Type 'exit' to stop.\n");

         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
         StringBuilder conversationHistory = new StringBuilder();

         OllamaService aiModel = new OllamaService();
         CommandHandler handler = new CommandHandler();
         AppConfig config = new AppConfig();

        while(true) {

            LocalDateTime currentTime = LocalDateTime.now();
            String time = currentTime.format(formatter).toUpperCase();
            String model = config.getCurrentConfig();

            System.out.print("[" + time + "] You : ");
            String userInput = scanner.nextLine();

            if(handler.handleCommand(userInput, conversationHistory, config)) continue;

            if(userInput.equalsIgnoreCase("/exit")) {
                System.out.println("AI Assistance Stopped!");
                break;
            }

            conversationHistory.append("User: ")
                    .append(userInput)
                    .append("\n");

            try {
                String aiResponse = aiModel.getAIResponse(conversationHistory.toString(), model);
                conversationHistory.append("AI Response: ")
                        .append(aiResponse)
                        .append("\n");

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