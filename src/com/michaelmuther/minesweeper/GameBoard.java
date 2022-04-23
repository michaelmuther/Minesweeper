package com.michaelmuther.minesweeper;

public class GameBoard {

    private final int mines;
    private final int BOARD_HEIGHT = 9;
    private final int BOARD_WIDTH = 9;
    private final char[][] gameBoard = new char[BOARD_HEIGHT][BOARD_WIDTH];
    private final char MINE = 'X';
    private final char SPACE = '.';

    public GameBoard(int mines) {
        this.mines = mines;
        generateGameBoard();
    }

    private void generateGameBoard() {
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
        updateMineCounter();
    }

    private void updateMineCounter() {
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (gameBoard[i][j] == SPACE) {
                    countMines(i, j);
                }
            }
        }
    }

    private void countMines(int index1, int index2) {
        int neighborMines = 0;
        // check upper left
        if (index1 >= 1 && index2 >= 1) {
            if (gameBoard[index1 - 1][index2 - 1] == MINE) {
                neighborMines++;
            }
        }
        // check above
        if (index2 >= 1) {
            if (gameBoard[index1][index2 - 1] == MINE) {
                neighborMines++;
            }
        }
        // check upper right
        if (index1 <= 7 && index2 >= 1) {
            if (gameBoard[index1 + 1][index2 - 1] == MINE) {
                neighborMines++;
            }
        }
        // check right
        if (index1 <= 7) {
            if (gameBoard[index1 + 1][index2] == MINE) {
                neighborMines++;
            }
        }
        // check below right
        if (index1 <= 7 && index2 <= 7) {
            if (gameBoard[index1 + 1][index2 + 1] == MINE) {
                neighborMines++;
            }
        }
        // check below
        if (index2 <= 7) {
            if (gameBoard[index1][index2 + 1] == MINE) {
                neighborMines++;
            }
        }
        // check below left
        if (index1 >= 1 && index2 <= 7) {
            if (gameBoard[index1 - 1][index2 + 1] == MINE) {
                neighborMines++;
            }
        }
        // check left
        if (index1 >= 1) {
            if (gameBoard[index1 - 1][index2] == MINE) {
                neighborMines++;
            }
        }
        if (neighborMines > 0) {
            gameBoard[index1][index2] = (char)(neighborMines + '0');
        }
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