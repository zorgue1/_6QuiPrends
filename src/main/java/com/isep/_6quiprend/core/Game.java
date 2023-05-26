package com.isep._6quiprend.core;

import java.util.ArrayList;
import java.util.List;

public class Game {

    public Game(){

    }

    private void initialize(){
        List<Card> allCard =new ArrayList<>();
        for (int i = 0; i <104; i++)
        {
            Card card = new Card(i);
            allCard.add(card);
        }
    }
}
