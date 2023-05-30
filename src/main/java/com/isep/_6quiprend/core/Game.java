package com.isep._6quiprend.core;

import java.util.*;

public class Game {

    private static int NBOFCARDBYDECK = 3;
    private List<Series> seriesListInTable = new ArrayList<>();
    private final List<Player> players;

    private List<Card> allCard;
    public Game(){
        this.allCard = Deck.createCards();
        this.players = new ArrayList<>();
    }

    public List<Series> getSeriesListInTable(){
        return this.seriesListInTable;
    }
    public List<Player> getPlayers(){
        return this.players;
    }

    public List<Card> getAllCard(){
        return this.allCard;
    }
    public void addPack(Player player, List<Card> cards){
        player.setPack(new RetrievedPack(cards));
    }
    public boolean areAllDeckEmpty(List<Player> players)
    {
        List<Boolean> stateList = new ArrayList<>();
        for (Player player : players)
        {
            Boolean isEmpty = player.getDeck().getCards().isEmpty();
            stateList.add(isEmpty);
        }
        if (stateList.indexOf(Boolean.FALSE) == -1)
            return true;
        else
            return false;
    }
    public void removeCard(Player player, Card card){
        List<Card> cards = player.getDeck().getCards();
        cards.remove(card);
        player.setDeck(new Deck(cards));
    }
    public void addInSeries(Series series, Card card){
        List<Card> cards = series.getCardsInTable();
        cards.add(card);
        series.setCardsInTable(cards);
    }
    public boolean isCardTooWeak(Card card){
        List<Boolean> list = new ArrayList<>();
        for (Series series : seriesListInTable)
        {
            list.add(series.getLastCardOf().getNumber() < card.getNumber());
        }
        if (list.indexOf(Boolean.TRUE) == -1)
            return true;
        else
            return false;

    }
    public Series getTheSeriesWithSmallestDifference(Card card){
        List<Integer> diffList = new ArrayList<>();
        List<Integer> positiveList = new ArrayList<>();
        for (Series series : this.seriesListInTable)
        {
            int difference = series.getDifferenceBetweenLastAndNew(card);
            diffList.add(difference);
            if (difference > 0)
                positiveList.add(difference);
        }
        int min = Collections.min(positiveList);

        int index = diffList.indexOf(min);
        return this.seriesListInTable.get(index);

    }



    public Deck getPlayerDeck(){
        List<Card> cardList = new ArrayList<>();
        for (int i = 0; i< NBOFCARDBYDECK; i++)
        {
            cardList.add(this.allCard.get(i));
            this.allCard.remove(this.allCard.get(i));
        }
        return new Deck(cardList);
    }

    public void initSeries(int position){
        List<Card> cardList = new ArrayList<>();
        cardList.add(this.allCard.get(0));
        this.allCard.remove(this.allCard.get(0));
        Series series = new Series(position, cardList);
        this.seriesListInTable.add(series);
    }



}
