package com.michaelmuther.minesweeper;

import java.util.Scanner;

public class GameInput {

    private final int index1;
    private final int index2;

    public GameInput() {
        final Scanner s = new Scanner(System.in);
        final String SET_MARKS = "Set/delete mines marks (x and y coordinates):";
        System.out.println(SET_MARKS);
        index2 = s.nextInt();
        index1 = s.nextInt();
    }

    public int getIndex1() {
        return index1;
    }

    public int getIndex2() {
        return index2;
    }
}
