package com.isep._6quiprend.core;

import java.util.*;

public class AI extends Player{

    public AI(String name, Deck deck, RetrievedPack pack) {
        super(name, deck, pack);
    }

    public Card getCard(List<Series> seriesList) {
       List<Card> cardList = new ArrayList<>();

        for (Card card : this.getDeck().getCards()) {
            List<Series> possibleSeries = new ArrayList<>();
            for (Series series : seriesList) {
                if (series.getLastCardOf().getNumber() < card.getNumber()) {
                    possibleSeries.add(series);
                }
            }
            if (!possibleSeries.isEmpty()) {
                cardList.add(card);
            }
        }

        if (cardList.size() > 0)
        {
            Random rd = new Random();
            int index = rd.nextInt(cardList.size());
            return cardList.get(index);
        }
        return null;
    }


    public Card getCardTooWeak(){
        List<Integer> numberList = new ArrayList<>();
        for (Card card : this.getDeck().getCards())
        {
            numberList.add(card.getNumber());
        }
        int min = Collections.min(numberList);
        int index = numberList.indexOf(min);
        return this.getDeck().getCards().get(index);
    }

    public Series getSeriesToRetrieve(List<Series> seriesList){
        List<Integer> pointList = new ArrayList<>();
        for (Series series : seriesList)
        {
            int point = series.getTotalBeefHead();
            pointList.add(point);
        }
        int min = Collections.min(pointList);
        int index = pointList.indexOf(min);
        return seriesList.get(index);
    }

}
