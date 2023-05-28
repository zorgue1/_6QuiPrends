package com.isep._6quiprend.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {

    private List<Card> deck;

    public Deck(List<Card> deck) {
        this.deck = deck;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    public static List<Card> createCards() {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < 104; i++) {
            cards.add(new Card(i));
        }
        return cards;
    }


    @Override
    public String toString() {
        return this.getDeck().toString();
    }
}
