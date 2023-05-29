package com.isep._6quiprend.console;

import com.isep._6quiprend.core.Card;
import com.isep._6quiprend.core.Series;

import java.util.List;

public class Display {
    public void printText(String text){
        System.out.println(text);
    }

    public void displaySeriesVertically(Series series){
        List<Card> cardList = series.getCardsInTable();
        for (Card card : cardList)
        {
            System.out.println("Card number " + card.getNumber() + "\n");
        }
    }

    public void displayAllSeries(List<Series> series) {
        for(int i = 0; i<4; i++)
        {
            System.out.print(i+1 + " ");
            displaySeriesVertically(series.get(i));
            System.out.println("");
        }
    }
}
