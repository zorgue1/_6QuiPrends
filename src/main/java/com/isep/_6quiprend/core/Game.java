package com.isep._6quiprend.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

        List<Integer> choosenNumberList = new ArrayList<>(); //si retourne nombre de la carte choisie
        for (Player player : players)
        {
            //choisir la carte
            int number = 2; //recuperer la valeur
            choosenNumberList.add(number);
        }

//        int minForBeginning = Collections.min(choosenNumberList);

        ////////a reflechir sur l'ordre de commencement des joueurs peut etre dictionnaire pour lier carte choisie et joueur/////////

        for (int number : choosenNumberList)
        {
            //recuperer la serie que le joueur a choisi
            Series choosenSeries = seriesListInTable.get(0); //ex
            Card playerCard = new Card(number);
            Card lastCardInSeries = choosenSeries.getLastCardOf();
            if (lastCardInSeries.getNumber() > playerCard.getNumber()) //carte + grande oui
                break;

            if (!getTheSeriesWithSmallestDifference(playerCard).equals(choosenSeries))
                break;
            if (choosenSeries.getNbOfCard() == 5)
            {
                seriesListInTable.set(0, Series.newSeries(playerCard)); //a modifier car ici utilise direct index tout dépend ce qui retourne
                break;
            }
            if (isCardTooWeak(playerCard))
            {
                //carte trop faible
                //choisi le paquet qu'il souhaite recuperer
                Series takenSeries = seriesListInTable.get(1);
                seriesListInTable.set(1, Series.newSeries(playerCard));
                break;
            }
        }


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
