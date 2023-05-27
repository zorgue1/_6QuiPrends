package com.isep._6quiprend.core;

import java.util.*;

public class Game {

    private List<Series> seriesListInTable;
    public Game(){
    }

    private void initialize(){

        List<Card> allCard = Deck.createCards();
        Collections.shuffle(allCard);

        List<Player> players = new ArrayList<>();
        int nbOfPlayer = 2; //a recuperer à partir du choix d'utilisateur
        //création des joueurs avec leur paquet de jeu
        for (int i = 1; i<=nbOfPlayer; i++)
        {
            String name = ""; //recuperer le nom saisi à partir de l'interface avec un index EX: name_1 pour player_1, name_2 pour player_2 etc
            Deck deck = getPlayerDeck(allCard);
            List<Card> retrivedCards = new ArrayList<>();
            RetrivedPack pack = new RetrivedPack(retrivedCards);
            players.add(new Player(name, deck, pack));
        }

        //poser 4 cartes sur la table

        for (int i = 0; i<4; i++)
        {
            List<Card> cardList = new ArrayList<>();
            cardList.add(allCard.get(i));
            Series series = new Series(cardList);
            seriesListInTable.add(series);
        }

        while (!areAllDeckEmpty(players))
        {
            List<Integer> choosenNumberList = new ArrayList<>(); //si retourne nombre de la carte choisie
            LinkedHashMap<Card, Player> getPlayerFromChoosenCard = new LinkedHashMap<>();
            for (Player player : players)
            {
                //choisir la carte
                int number = 2; //recuperer la valeur
                choosenNumberList.add(number);
                getPlayerFromChoosenCard.put(new Card(number), player); //si renvoie nombre
            }

            int minForBeginning = Collections.min(choosenNumberList);

            Collections.sort(choosenNumberList);


            for (int number : choosenNumberList)
            {
                Card playerCard = new Card(number);
                System.out.println(getPlayerFromChoosenCard.get(playerCard).getName()); // il faut afficher le player qui joue


                boolean isPossible = false;
                while (!isPossible)
                {
                    //recuperer la serie que le joueur a choisi
                    Series choosenSeries = seriesListInTable.get(0); //ex
                    Card lastCardInSeries = choosenSeries.getLastCardOf();


                    if (lastCardInSeries.getNumber() < playerCard.getNumber()) //carte joueur + grande oui
                    {
                        if (getTheSeriesWithSmallestDifference(playerCard).equals(choosenSeries))
                        {
                            if (choosenSeries.getNbOfCard() == 5)
                            {
                                seriesListInTable.set(0, Series.newSeries(playerCard)); //a modifier car ici utilise direct index tout dépend ce qui retourne
                                removeCard(getPlayerFromChoosenCard.get(playerCard), playerCard);
                                isPossible = true; // sortir de la boucle while
                            }
                            else
                            {
                                removeCard(getPlayerFromChoosenCard.get(playerCard), playerCard);
                                addInSeries(choosenSeries, playerCard);
                                addPack(getPlayerFromChoosenCard.get(playerCard), choosenSeries.getCardsInTable());
                                isPossible = true;
                            }
                        }
                    }
                    else if (isCardTooWeak(playerCard))
                    {
                        //carte trop faible
                        //choisi le paquet qu'il souhaite recuperer
                        Series takenSeries = seriesListInTable.get(1); //choisi serie 1
                        seriesListInTable.set(1, Series.newSeries(playerCard)); //nouvelle serie 1 avec la carte joueur
                        removeCard(getPlayerFromChoosenCard.get(playerCard), playerCard);
                        addPack(getPlayerFromChoosenCard.get(playerCard), takenSeries.getCardsInTable());
                        isPossible = true;
                    }
                }
            }

        }

        List<Integer> pointList = new ArrayList<>();
        HashMap<Integer, Player> getPlayerByPoint = new HashMap<>();
        for (Player player : players)
        {
            int point = player.getPack().getTotalBeefHead();
            pointList.add(point);
            getPlayerByPoint.put(point, player);
        }

        int minPoint = Collections.min(pointList);
        //annoncer le gagnant
        System.out.println("The winner is " + getPlayerByPoint.get(minPoint));
    }

    public void addPack(Player player, List<Card> cards){
        player.setPack(new RetrivedPack(cards));
    }
    public boolean areAllDeckEmpty(List<Player> players)
    {
        List<Boolean> stateList = new ArrayList<>();
        for (Player player : players)
        {
            Boolean isEmpty = player.getDeck().getDeck().isEmpty();
            stateList.add(isEmpty);
        }
        if (stateList.indexOf(Boolean.FALSE) == -1)
            return true;
        else
            return false;
    }
    public void removeCard(Player player, Card card){
        List<Card> cards = player.getDeck().getDeck();
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
        for (Series series : seriesListInTable)
        {
            int difference = series.getDifferenceBetweenLastAndNew(card);
            diffList.add(difference);
        }
        int min = Collections.min(diffList);
        int index = diffList.indexOf(min);
        return seriesListInTable.get(index);

    }



    private Deck getPlayerDeck(List<Card> cards){
        List<Card> cardList = new ArrayList<>();
        for (int i = 0; i< 10; i++)
        {
            cardList.add(cards.get(i));
            cards.remove(cards.get(i));
        }
        return new Deck(cardList);
    }




}
