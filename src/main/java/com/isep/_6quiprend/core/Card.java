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

        int beefHead = 0 ;


        if (number % 10 == 5) {
            beefHead += 2;
        } else if (number % 10 == 0) {
            beefHead += 3;
        } else if (number == 11 || number == 22) {
            beefHead += 5;
        } else {
            beefHead = 1;
        }

        this.beefHead = beefHead ;

    }
}


