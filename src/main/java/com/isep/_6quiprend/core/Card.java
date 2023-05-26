package com.isep._6quiprend.core;

import java.net.URL;
import javax.swing.ImageIcon;

public class Card {

    private int number ;
    private int beefHead ;
    private ImageIcon imageIcon;

    public Card(int number) {
        this.number = number;
        setBeefHead(number);
        URL url = getClass().getResource("/image/"+number+".png");
        if(url!=null) this.imageIcon = new ImageIcon(url);
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
        } else if (number == 11 || number == 22) { //
            beefHead += 5;
        } else {
            beefHead = 1;
        }

        this.beefHead = beefHead ;

    }
}


