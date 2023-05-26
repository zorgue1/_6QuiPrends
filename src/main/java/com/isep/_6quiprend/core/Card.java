package com.isep._6quiprend.core;

import java.net.URL;
import javax.swing.ImageIcon;

public class Card {

    private int number ;
    private int beefHead ;
    private ImageIcon imageIcon;

    public Card(int number, int beefHead) {
        this.number = number;
        this.beefHead = beefHead;
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

    public static int getCardBeefHead(int number) {
        if (number == 55) {
            return 7;
        } else if (number % 11 == 0) {
            return 5;
        } else if(number % 10 == 0) {
            return 3;
        } else if (number % 5 == 0) {
            return 2;
        } else {
            return 1;
        }
    }
}


