package com.isep._6quiprend.core;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private Deck deck;
    private String name;

    private RetrivedPack pack;

    public Player(String name, Deck deck, RetrivedPack pack)
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

    public RetrivedPack getPack() {
        return pack;
    }

    public void setPack(RetrivedPack pack) {
        this.pack = pack;
    }
}
