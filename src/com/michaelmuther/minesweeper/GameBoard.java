package com.michaelmuther.minesweeper;

public class GameBoard {

    private final int mines;
    private final int BOARD_HEIGHT = 9;
    private final int BOARD_WIDTH = 9;
    private final char[][] gameBoard = new char[BOARD_HEIGHT][BOARD_WIDTH];

    public GameBoard(int mines) {
        this.mines = mines;
        generateGameBoard();
    }

    private void generateGameBoard() {
        final char MINE = 'X';
        final char SPACE = '.';
        double mineFactor = (double) mines / (double) (BOARD_HEIGHT * BOARD_WIDTH);
//        System.out.println(mineFactor); // TEST
        int totalMines;
        do {
            totalMines = 0;
            for (int i = 0; i < BOARD_HEIGHT; i++) {
                for (int j = 0; j < BOARD_WIDTH; j++) {
                    double random = Math.random();
                    if (random <= mineFactor) {
                        gameBoard[i][j] = MINE;
                        totalMines++;
                    } else {
                        gameBoard[i][j] = SPACE;
                    }
                }
            }
//            System.out.println(totalMines); // TEST
        } while (mines != totalMines);
    }

    public char[][] getGameBoard() {
        return gameBoard;
    }

}

//    private char[][] gameBoard = {
//            {'.', '.', 'X', '.', '.', '.', '.', '.', '.'},
//            {'.', '.', 'X', '.', '.', '.', '.', '.', '.'},
//            {'.', '.', 'X', '.', '.', '.', '.', '.', '.'},
//            {'.', '.', 'X', '.', '.', '.', '.', '.', '.'},
//            {'.', '.', 'X', '.', '.', '.', '.', '.', '.'},
//            {'.', '.', 'X', '.', '.', '.', '.', '.', '.'},
//            {'.', '.', 'X', '.', '.', '.', '.', '.', '.'},
//            {'.', '.', 'X', '.', '.', '.', '.', '.', '.'},
//            {'.', '.', 'X', '.', '.', '.', '.', '.', '.'}
//    };