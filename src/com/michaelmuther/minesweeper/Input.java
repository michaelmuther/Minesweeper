package com.michaelmuther.minesweeper;

import java.util.Scanner;

public class Input {

    private final int mines;

    public Input() {
        Scanner s = new Scanner(System.in);
        final String INPUT = "How many mines do you want on the field?";
        System.out.println(INPUT);
        mines = s.nextInt();
    }

    public int getMines() {
        return mines;
    }
}
