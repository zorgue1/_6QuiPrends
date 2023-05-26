package com.isep._6quiprend.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

    public Game(){
    }

    private void initialize(){
        List<Card> allCard = new ArrayList<>();
        for (int i = 0; i <104; i++)
        {
            Card card = new Card(i);
            allCard.add(card);
        }

        Collections.shuffle(allCard);

        Deck deck1 = getPlayerDeck(allCard);
        Deck deck2 = getPlayerDeck(allCard);

        String name1 = "nom1"; //valeurs à recuperer
        String name2 = "nom2"; //
        Player player1 = new Player(name1, deck1);
        Player player2 = new Player(name2, deck2);
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
