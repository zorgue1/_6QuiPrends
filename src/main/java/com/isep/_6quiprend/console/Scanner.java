package com.isep._6quiprend.console;

import java.io.InputStream;

public class Scanner {
    java.util.Scanner scanner;

    public Scanner(InputStream is) {
        scanner = new java.util.Scanner(is);
    }

    public String getText(){
        return scanner.next();
    }


    public int getInteger(){
        return scanner.nextInt();
    }
}
