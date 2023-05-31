package com.isep._6quiprend.core;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    private static int MIN_VALUE = 1;
    private static int MAX_VALUE = 104;

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
        for (int i = MIN_VALUE; i <= MAX_VALUE; i++) {
            cards.add(new Card(i));
        }
        return cards;
    }


    @Override
    public String toString() {
        return this.getCards().toString();
    }

    public int getTotalBeefHead(){
        int total = 0;
        for (Card card : this.getCards())
        {
            total = total + card.getCardBeefHead();
        }
        return total;
    }

    public boolean hasCardWithNumber(int number, Deck deck) {
        for (Card card : deck.getCards()) {
            if (card.getNumber() == number) {
                return true;
            }
        }
        return false;
    }
}
