package com.isep._6quiprend.console;

import com.isep._6quiprend.core.Deck;

import java.io.InputStream;
import java.util.InputMismatchException;

public class Scanner {
    java.util.Scanner scanner;

    public Scanner(InputStream is) {
        scanner = new java.util.Scanner(is);
    }

    public String getText(){
        return scanner.next();
    }


    public int getInteger() {
        return scanner.nextInt();
    }

    public boolean isValidIntegerInRange(int number, int min, int max) {
        return number >= min && number <= max;
    }

    public int getIntegerInRange(int min, int max) {
        int number;
        boolean validInput;
        do {
            validInput = true;
            try {
                number = getInteger();
                if (!isValidIntegerInRange(number, min, max)) {
                    System.out.println("Error: Enter a number within the specified range (" + min + " - " + max + ")");
                    validInput = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Enter a valid integer");
                validInput = false;
                scanner.nextLine();
                number = min - 1;
            }
        } while (!validInput);
        return number;
    }

    private boolean isValidYesNoResponse(String response) {
        return response.equalsIgnoreCase("Yes") || response.equalsIgnoreCase("No");
    }

    public String getTextWithPrompt(String prompt) {
        String response;
        do {
            System.out.println(prompt);
            response = getText();
            if (!isValidYesNoResponse(response)) {
                System.out.println("Error: Enter a valid answer (Yes/No)");
            }
        } while (!isValidYesNoResponse(response));
        return response;
    }

    private boolean isValidCardNumberInput(int number, Deck deck) {
        return deck.hasCardWithNumber(number);
    }

    public int getCardNumberInput(Deck deck) {
        int number;
        boolean validInput;
        do {
            validInput = true;
            try {
                number = getInteger();
                if (!isValidCardNumberInput(number, deck)) {
                    System.out.println("Error: Enter a number that you have");
                    validInput = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Enter a valid integer");
                validInput = false;
                scanner.nextLine();
                number = -1;
            }
        } while (!validInput);
        return number;
    }



}
