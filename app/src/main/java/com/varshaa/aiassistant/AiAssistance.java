package com.varshaa.aiassistant;

import com.varshaa.aiassistant.command.CommandHandler;
import com.varshaa.aiassistant.config.AppConfig;
import com.varshaa.aiassistant.service.OllamaService;
import com.varshaa.aiassistant.storage.ConversationStorage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class AiAssistance {

     public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

         OllamaService aiModel = new OllamaService();
         CommandHandler handler = new CommandHandler();
         AppConfig config = new AppConfig();
         ConversationStorage conversationStrg = new ConversationStorage();

         System.out.println("""
                 =====================================
                         AI CLI ASSISTANT
                 =====================================
                 Model : %s
                 Role  : %s
                 
                 Commands:
                     /help
                     /history
                     /clear
                     /role
                     /model
                     /exit
                 =====================================
                 """.formatted(
                 config.getCurrentConfig(),
                 config.getCurrentRole()
         ));

         StringBuilder conversationHistory = new StringBuilder(conversationStrg.loadConversation());

         while(true) {

            LocalDateTime currentTime = LocalDateTime.now();
            String time = currentTime.format(formatter).toUpperCase();
            String model = config.getCurrentConfig();
            String role = config.getCurrentRole();

            System.out.print("[" + time + "] You : ");
            String userInput = scanner.nextLine();

             if(userInput.trim().isEmpty()) {
                 System.out.println("Input cannot be empty.");
                 continue;
             }

            if(handler.handleCommand(userInput, conversationHistory, config, conversationStrg)) continue;

            if(userInput.equalsIgnoreCase("/exit")) {
                System.out.println("AI Assistance Stopped!");
                break;
            }

            conversationHistory.append("User: ")
                    .append(userInput)
                    .append("\n");

            try {
                String aiResponse = aiModel.getAIResponse(conversationHistory.toString(), model, role);
                conversationHistory.append("AI Response: ")
                        .append(aiResponse)
                        .append("\n");

                System.out.println("\n[" + time + "]AI : ");
                conversationStrg.saveConversation(conversationHistory.toString());
                System.out.println(aiResponse);
            } catch (Exception e) {
                System.out.println("Unable to Connect!");
                System.out.println(e);
            }
        }

        scanner.close();
    }
}