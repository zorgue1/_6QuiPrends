package com.isep._6quiprend.core;

import java.util.List;

public class RetrievedPack {

    private List<Card> pack;

    public RetrievedPack(List<Card> pack) {
        this.pack = pack;
    }

    public int getTotalBeefHead(){
        int total = 0;
        System.out.println("card for beef : " + this.pack.toString());
        for (Card card : this.pack)
        {
            System.out.println("card " + card.toString());
            System.out.println("beef " + card.getCardBeefHead());
            total = total + card.getCardBeefHead();
        }
        return total;
    }

    public List<Card> getCardsInPack() {
        return pack;
    }

    @Override
    public String toString() {
        return this.getCardsInPack().toString();
    }
}
