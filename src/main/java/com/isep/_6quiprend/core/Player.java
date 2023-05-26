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




}
