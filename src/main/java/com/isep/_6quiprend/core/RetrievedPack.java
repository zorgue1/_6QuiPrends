package com.isep._6quiprend.core;

import java.util.List;

public class RetrievedPack extends Deck{


    public RetrievedPack(List<Card> cards) {
        super(cards);
    }



//    public List<Card> getCardsInPack() {
//        return cards;
//    }

    @Override
    public String toString() {
        return this.getCards().toString();
    }
}
