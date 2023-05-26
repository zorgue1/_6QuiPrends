package com.isep._6quiprend.core;

import java.util.List;

public class Series {

    private List<Card> cardsInTable;
    public Series(List<Card> cardsInTable)
    {
        this.cardsInTable = cardsInTable;
    }

    public Card getLastCardOf(){
        return this.cardsInTable.get(cardsInTable.size()-1);
    }
}
