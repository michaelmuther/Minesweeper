package com.michaelmuther.minesweeper;

public class Output {

    private final char[][] gameBoard;

    public Output(char[][] gameBoard){
        this.gameBoard = gameBoard;
        printBoard();
    }

    private void printBoard() {
        final String TOP_ROW_HEADER = " |123456789|";
        final String ROW_BORDER = "-|---------|";
        int i = 1;
        System.out.println(TOP_ROW_HEADER);
        System.out.println(ROW_BORDER);
        for (char[] array:gameBoard) {
            System.out.print(i + "|");
            for (char field:array) {
                if (field == 'X') {
                    System.out.print('.');
                } else {
                    System.out.print(field);
                }
            }
            System.out.print("|");
            System.out.println();
            i++;
        }
        System.out.println(ROW_BORDER);
    }
}
