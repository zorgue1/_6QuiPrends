package com.isep._6quiprend.core;

import java.util.List;

public class Player {

    private Deck deck;
    private String name;

    private RetrievedPack pack;

    public Player(String name, Deck deck, RetrievedPack pack)
    {
        this.name = name;
        this.deck = deck;
        this.pack = pack;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public String getName() {
        return name;
    }

    public RetrievedPack getPack() {
        return pack;
    }

    public void setPack(RetrievedPack pack) {
        this.pack = pack;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public void removeCard(Card card){
        List<Card> cards = this.getDeck().getCards();
        cards.remove(card);
        setDeck(new Deck(cards));
    }


}
