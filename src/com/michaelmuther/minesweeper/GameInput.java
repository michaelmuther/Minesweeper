package com.michaelmuther.minesweeper;

import java.util.Scanner;

public class GameInput {

    private final Scanner s = new Scanner(System.in);
    private final String SET_MARKS = "Set/unset mines marks or claim a cell as free:";
    private final int row;
    private final int col;
    private boolean isFreeInput = false;
    private final String input;

    public GameInput() {
        System.out.println(SET_MARKS);
        col = s.nextInt();
        row = s.nextInt();
        input = s.next();
        if (input.equals("free")) {
            isFreeInput = true;
        }
//        System.out.println(isFreeInput); // TEST
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isFreeInput() {
        return isFreeInput;
    }
}
