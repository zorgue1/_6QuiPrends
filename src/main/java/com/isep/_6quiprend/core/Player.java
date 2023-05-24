package com.isep._6quiprend.core;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private Deck deck;

    public Player(Deck deck)
    {
        this.deck = deck;
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
