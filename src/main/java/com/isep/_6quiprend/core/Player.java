package com.isep._6quiprend.core;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private Deck deck;
    private String name;

    public Player(Deck deck,String name)
    {
        this.deck = deck;
        this.name=name;
    }

    public Deck assignDeck() {
        List<Card> cards = new ArrayList<>();
        while (cards.size() < 11) {
            Card newCard = Deck.assignCard();
            boolean isUnique = true;

            for (Card cardInDeck : cards) {
                if (cardInDeck.getNumber() == newCard.getNumber()) {
                    isUnique = false;
                    break;
                }
            }

            if (isUnique) {
                cards.add(newCard);
            }
        }

        Deck deck = new Deck(cards);
        return deck;
    }

}
