package com.isep._6quiprend.core;

public class Card {

    private int number ;
    private int beefHead ;

    public Card(int number) {
        this.number = number;
        setBeefHead(number);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getBeefHead() {
        return beefHead;
    }

    public void setBeefHead(int number) {
        this.beefHead = beefHead ;
    }
}


