package com.isep._6quiprend.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.abs;

public class Series {

    private List<Card> cardsInTable;
    private int position;
    public Series(int position, List<Card> cardsInTable)
    {
        this.position = position;
        this.cardsInTable = cardsInTable;
    }

    @Override
    public String toString() {
        return "Series " + position;
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
        return nbOfNewCard - nbOfLastCard;
    }

    public int getNbOfCard(){
        return this.cardsInTable.size();
    }

    public static Series newSeries(int position, Card card){
        List<Card> cardList = new ArrayList<>();
        cardList.add(card);
        return new Series(position, cardList);
    }

    public int getPosition() {
        return position;
    }

}

