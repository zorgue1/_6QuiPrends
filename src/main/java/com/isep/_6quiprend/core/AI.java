package com.isep._6quiprend.core;

import java.util.*;

public class AI extends Player{

    public AI(String name, Deck deck, RetrievedPack pack) {
        super(name, deck, pack);
    }

    public LinkedHashMap<Card, Series> chooseCard(List<Series> seriesList){

        HashMap<Card, List<Series>> hashMap = new HashMap<>();
        LinkedHashMap<Card, Series> hashMap1 = new LinkedHashMap<>();
        for (Card card : this.getDeck().getCards())
        {
            List<Series> possibleSeries = new ArrayList<>();
            for (Series series : seriesList)
            {
                if (series.getLastCardOf().getNumber() < card.getNumber())
                {
                    possibleSeries.add(series);
                }
            }

            if (seriesList.size() > 0)
                hashMap.put(card, seriesList);
        }

        for (Card possibleCard : hashMap.keySet())
        {
            List<Integer> diffList = new ArrayList<>();
            List<Series> possibleSeriesList = hashMap.get(possibleCard);
            for (Series series: possibleSeriesList)
            {
                diffList.add(series.getDifferenceBetweenLastAndNew(possibleCard));
            }
            int min = Collections.min(diffList);
            int index = diffList.indexOf(min);
            Series choice = possibleSeriesList.get(index);
            hashMap1.put(possibleCard, choice);
        }
        return hashMap1;
    }

    public Card getCardFromHashMap(LinkedHashMap<Card, Series> hashMap, int index){
        int currentIndex = 0;
        Card card1 = null;
        for (Card card : hashMap.keySet()) {
            if (currentIndex == index) {
                card1 = card;
                break; // Sortir de la boucle une fois l'entrée trouvée
            }
            currentIndex++;
        }
        return card1;
    }

    public Card getCardTooWeak(){
        Random rd = new Random();
        int index = rd.nextInt(this.getDeck().getCards().size());
        Card card = this.getDeck().getCards().get(index);
        return card;
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
