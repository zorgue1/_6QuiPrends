package com.isep._6quiprend.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.abs;

public class Series {

    private List<Card> cardsInTable;
    public Series(List<Card> cardsInTable)
    {
        this.cardsInTable = cardsInTable;
    }

    public List<Card> getCardsInTable() {
        return cardsInTable;
    }

    public void setCardsInTable(List<Card> cardsInTable) {
        this.cardsInTable = cardsInTable;
    }

    public Card getLastCardOf(){
        return this.cardsInTable.get(cardsInTable.size()-1);
    }

    public int getDifferenceBetweenLastAndNew(Card newCard){
        int nbOfLastCard = getLastCardOf().getNumber();
        int nbOfNewCard = newCard.getNumber();
        return abs(nbOfLastCard - nbOfNewCard);
    }

    public int getNbOfCard(){
        return this.cardsInTable.size();
    }

    public static Series newSeries(Card card){
        List<Card> cardList = Arrays.asList(card);
        return new Series(cardList);
    }
}
