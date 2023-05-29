package com.isep._6quiprend.core;

import java.net.URL;
import java.util.Objects;
import javax.swing.ImageIcon;

public class Card {

    private int number ;
    private int beefHead ;
    private ImageIcon imageIcon;

    public Card(int number) {
        this.number = number;
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

    public int getCardBeefHead() {
        if (this.number == 55) {
            return 7;
        } else if (this.number % 11 == 0) {
            return 5;
        } else if(this.number % 10 == 0) {
            return 3;
        } else if (this.number % 5 == 0) {
            return 2;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return "Card number " + this.getNumber();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return getNumber() == card.getNumber();
    }
}


