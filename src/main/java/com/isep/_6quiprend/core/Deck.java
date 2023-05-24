package com.isep._6quiprend.core;

import java.util.ArrayList;
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

    public static Card assignCard(){
        Random random = new Random();
        int number = random.nextInt(1,104);
        Card card = new Card(number);
        return card;
    }

}
