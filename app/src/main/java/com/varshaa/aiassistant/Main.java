package com.varshaa.aiassistant;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Ask something: ");

        String userInput = scanner.nextLine();
        System.out.println("AI Assistant received: " + userInput);

        scanner.close();
    }
}