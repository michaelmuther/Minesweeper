package com.michaelmuther.minesweeper;

public class GameBoard {

    private final int mines;
    private final int BOARD_HEIGHT = 9;
    private final int BOARD_WIDTH = 9;
    private final char[][] gameBoard = new char[BOARD_HEIGHT][BOARD_WIDTH];
    private final char[][] markedGameBoard;
    private final char MINE = 'X';
    private final char SPACE = '.';
    private int markedMines = 0;
    private int marks = 0;

    public GameBoard(int mines) {
        this.mines = mines;
        generateGameBoard();
        markedGameBoard = gameBoard;
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

    public boolean setMark(int index1, int index2) {
        final char MARK = '*';
        final int INDEX_SHIFT = 1;
        if (markedGameBoard[index1 - INDEX_SHIFT][index2 - INDEX_SHIFT] == MINE) {
            markedGameBoard[index1 - INDEX_SHIFT][index2 - INDEX_SHIFT] = MARK;
            markedMines++;
            marks++;
            return true;
        } else if (markedGameBoard[index1 - INDEX_SHIFT][index2 - INDEX_SHIFT] == SPACE) {
            markedGameBoard[index1 - INDEX_SHIFT][index2 - INDEX_SHIFT] = MARK;
            marks++;
            return true;
        } else if (markedGameBoard[index1 - INDEX_SHIFT][index2 - INDEX_SHIFT] == MARK) {
            if (gameBoard[index1 - INDEX_SHIFT][index2 - INDEX_SHIFT] == MINE) {
                markedGameBoard[index1 - INDEX_SHIFT][index2 - INDEX_SHIFT] = MINE;
                markedMines--;
                marks--;
                return true;
            } else {
                markedGameBoard[index1 - INDEX_SHIFT][index2 - INDEX_SHIFT] = SPACE;
                marks--;
                return true;
            }
        } else { // field is a number
            return false;
        }
    }

    public char[][] getGameBoard() {
        return gameBoard;
    }

    public char[][] getMarkedGameBoard() {
        return markedGameBoard;
    }

    public int getMines() {
        return mines;
    }

    public int getMarkedMines() {
        return markedMines;
    }

    public int getMarks() {
        return marks;
    }
}
