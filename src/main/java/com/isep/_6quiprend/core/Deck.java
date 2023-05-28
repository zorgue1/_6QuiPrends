package com.isep._6quiprend.core;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    private List<Card> cards;

    public Deck(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setDeck(List<Card> deck) {
        this.cards = cards;
    }

    public static List<Card> createCards() {
        List<Card> cards = new ArrayList<>();
        for (int i = 1; i <= 104; i++) {
            cards.add(new Card(i));
        }
        return cards;
    }


    @Override
    public String toString() {
        return this.getCards().toString();
    }
}
