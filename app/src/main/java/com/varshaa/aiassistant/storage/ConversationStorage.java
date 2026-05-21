package com.varshaa.aiassistant.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConversationStorage {

    private static final String FILE_NAME = "conversation.txt";

    public void saveConversation(String conversationHistory) {

        try {
            Files.writeString(Path.of(FILE_NAME), conversationHistory);
        } catch (IOException e) {
            System.out.println("Unable to save conversation!");
        }
    }

    public String loadConversation() {

        try {
            if (Files.exists(Path.of(FILE_NAME))) return Files.readString(Path.of(FILE_NAME));

        } catch (IOException e) {
            System.out.println("Unable to load conversation!");
        }
        return "";
    }

    public void exportConversation(String conversationHistory) {

        try {

            Files.createDirectories(Path.of("conversations"));
            String fileName = "conversations/chat-" + System.currentTimeMillis() + ".txt";

            Files.writeString(
                    Path.of(fileName),
                    conversationHistory
            );
            System.out.println("Conversation exported to : " + fileName);
        } catch (IOException e) {
            System.out.println("Unable to export conversation!");
        }
    }

    public void listConversations() {
        File folder = new File("conversations");
        File[] files = folder.listFiles();

        if(files == null || files.length == 0) {
            System.out.println("No Conversations to show");
            return;
        }

        for(File file : files) System.out.println(file.getName());
    }
}