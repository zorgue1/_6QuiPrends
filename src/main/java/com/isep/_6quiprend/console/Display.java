package com.isep._6quiprend.console;

import com.isep._6quiprend.core.Card;
import com.isep._6quiprend.core.Series;

import java.util.List;

public class Display {
    public void printText(String text){
        System.out.println(text);
    }

    public void displayAllSeries(List<Series> series) {
        int maxSize = Math.max(
                Math.max(series.get(0).getNbOfCard(), series.get(1).getNbOfCard()),
                Math.max(series.get(2).getNbOfCard(), series.get(3).getNbOfCard())
        );

        System.out.print("            ");
        for (int i = 0; i < series.size(); i++) {
            System.out.printf("Series %d\t\t", i + 1);
        }
        System.out.println();

        System.out.print("            ");
        for (int i = 0; i < series.size(); i++) {
            System.out.print("---------------\t");
        }
        System.out.println();

        for (int i = 0; i < maxSize; i++) {
            for (int j = 0; j < series.size(); j++) {
                List<Card> cards = series.get(j).getCardsInTable();
                String element = i < cards.size() ? cards.get(i).toString() : "";
                if (j == 0)
                    System.out.print("            ");
                System.out.printf("%-15s|", element);
            }
            System.out.println();
        }
    }


    public void printTextInRed(String text){
        System.out.println("\u001B[31m" + text + "\u001B[0m");
    }

    public void printTextInBlue(String text){
        System.out.println("\u001B[34m" + text + "\u001B[0m");
    }

    public void printImportantInfo(String text){
        System.out.println("\u001B[1;33m" + "--------" + text + "--------" + "\u001B[0m");
    }


}
