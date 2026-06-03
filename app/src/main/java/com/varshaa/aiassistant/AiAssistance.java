package com.varshaa.aiassistant;

import com.varshaa.aiassistant.command.CommandHandler;
import com.varshaa.aiassistant.config.AppConfig;
import com.varshaa.aiassistant.service.OllamaService;
import com.varshaa.aiassistant.storage.ConversationManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AiAssistance {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("hh:mm a");
        OllamaService aiModel = new OllamaService();
        AppConfig config = new AppConfig();
        ConversationManager conversationManager = new ConversationManager();
        CommandHandler handler = new CommandHandler(conversationManager, config, aiModel);
        LocalDateTime startTime = LocalDateTime.now();
        String sessionStartTime = startTime.format(formatter);

        System.out.println("""
                 =====================================
                         AI CLI ASSISTANT
                 =====================================
                 Model : %s
                 Role  : %s
                 Session StartTime : %s

                 Commands:
                     /help
                     /history
                     /clear
                     /role
                     /model
                     /save
                     /about
                     /new
                     /status
                     /sessions
                     /exit
                     /summary
                 =====================================
                 """.formatted(config.getCurrentConfig(), config.getCurrentRole(), sessionStartTime));

        while(true) {
            LocalDateTime currentTime =
                    LocalDateTime.now();
            String time = currentTime
                            .format(formatter)
                            .toUpperCase();
            String model = config.getCurrentConfig();
            String role = config.getCurrentRole();
            System.out.print("[" + time + "] You : ");

            String userInput = scanner.nextLine();

            if(userInput.trim().isEmpty()) {
                System.out.println("Input cannot be empty.");
                continue;
            }

            if(handler.handleCommand(userInput)) continue;

            if(userInput.equalsIgnoreCase("/exit")) {
                System.out.println("AI Assistance Stopped!");
                break;
            }

            conversationManager.addUserMessage(userInput);

            try {

                System.out.print("\n[" + time + "]AI : ");
                String aiResponse = aiModel.getAIResponse(conversationManager.getCurrentConversationContext(), model,
                        role);
                conversationManager.addAIMessage(aiResponse);

            } catch (Exception e) {

                System.out.println("Unable to Connect!");
                System.out.println(e);
            }
        }

        scanner.close();
    }
}