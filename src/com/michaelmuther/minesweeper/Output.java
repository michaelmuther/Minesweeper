package com.michaelmuther.minesweeper;

public class Output {

    private char[][] printGameBoard;
    private final String TOP_ROW_HEADER = " |123456789|";
    private final String ROW_BORDER = "-|---------|";
    private final char MINE = 'X';
    private final char UNEXPLORED_CELL = '.';

    public Output () {
        printBoardEmpty();
    }

    public Output(char[][] printGameBoard){
        this.printGameBoard = printGameBoard;
        printBoardTurn();
    }

    public Output(char[][] printGameBoard, boolean gameOver) {
        this.printGameBoard = printGameBoard;
        printBoardGameOver();
    }

    private void printBoardEmpty() {
        System.out.println(TOP_ROW_HEADER);
        System.out.println(ROW_BORDER);
        for (int i = 1; i <= 9; i++) {
            System.out.println(i + "|.........|");
        }
        System.out.println(ROW_BORDER);
    }

    // prints a dot if the field is empty or is a mine; prints the number of surrounding mines
    private void printBoardTurn() {
        int i = 1;
        System.out.println(TOP_ROW_HEADER);
        System.out.println(ROW_BORDER);
        for (char[] array:printGameBoard) {
            System.out.print(i + "|");
            for (char field:array) {
                if (field == MINE) { // if gameBoard field is a mine, print it as unexplored during turns.
                    System.out.print(UNEXPLORED_CELL);
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

    private void printBoardGameOver() {
        int i = 1;
        System.out.println(TOP_ROW_HEADER);
        System.out.println(ROW_BORDER);
        for (char[] array:printGameBoard) {
            System.out.print(i + "|");
            for (char field:array) {
                System.out.print(field);
            }
            System.out.print("|");
            System.out.println();
            i++;
        }
        System.out.println(ROW_BORDER);
    }
}
