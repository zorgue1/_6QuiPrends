package com.isep._6quiprend.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

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
        List<Series> seriesListInTable = new ArrayList<>();
        for (int i = 0; i<4; i++)
        {
            List<Card> cardList = new ArrayList<>();
            cardList.add(allCard.get(i));
            Series series = new Series(cardList);
            seriesListInTable.add(series);
        }



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
