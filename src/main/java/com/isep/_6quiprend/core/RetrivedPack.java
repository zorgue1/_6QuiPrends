package com.isep._6quiprend.core;

import java.util.List;

public class RetrivedPack {

    private List<Card> pack;

    public RetrivedPack(List<Card> pack) {
        this.pack = pack;
    }

    public int getTotalBeefHead(){
        int total = 0;
        for (Card card : this.pack)
        {
            total += card.getBeefHead();
        }
        return total;
    }
}
