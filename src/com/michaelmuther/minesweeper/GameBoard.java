package com.michaelmuther.minesweeper;

public class GameBoard {

    private final int mines;
    private final int BOARD_HEIGHT = 9;
    private final int BOARD_WIDTH = 9;
    private final char[][] gameBoard = new char[BOARD_HEIGHT][BOARD_WIDTH]; // this is the original gameboard
    private char[][] markedGameBoard = new char[BOARD_HEIGHT][BOARD_WIDTH]; // this is the updated gameboard.
    private char[][] preMarkedGameBoard = new char[BOARD_HEIGHT][BOARD_WIDTH]; // this is the board when a mark is chosen before a free space
    private final char MINE = 'X';
    private final char MARK = '*';
    private final char UNEXPLORED_CELL = '.';
    private final char EXPLORED_CELL = '/';
    private int markedMines = 0;
    private int marks = 0;
    private int unexploredCells;
    private final int initRow;
    private final int initCol;
    private final int INDEX_SHIFT = 1;
    private final int MIN_INDEX_TO_BE_REDUCED = 2 - INDEX_SHIFT; // row or col must be >= 2 to check cell lower
    private final int MAX_INDEX_TO_BE_INCREASED = 8 - INDEX_SHIFT; // row or col must be <= 8 to check cell higher
    private boolean hitMine = false;

    public GameBoard(int mines, int initRow, int initCol, boolean isFreeInput) {
        this.mines = mines;
        this.initRow = initRow;
        this.initCol = initCol;
        generateGameBoard();
//        markedGameBoard = gameBoard.clone();  clone is not the same as below.
//        System.arraycopy(gameBoard, 0 , markedGameBoard, 0, 9); Also does not work
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (gameBoard[i][j] == MINE) {
                    markedGameBoard[i][j] = MINE;
                } else {
                    markedGameBoard[i][j] = UNEXPLORED_CELL;
                }
//                System.out.print(markedGameBoard[i][j]); // TEST
            }
//            System.out.println(); // TEST
        }
        if (isFreeInput) {
            exploreCells(initRow, initCol);
        } else {
            setMark(initRow, initCol);
        }
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
                        // ---- TEST CODE ----
//                            int rowIndex = i + 1;
//                            int colIndex = j + 1;
//                            System.out.println(rowIndex + " " + colIndex);
                        // END TEST CODE
                        totalMines++;
                    } else {
                        gameBoard[i][j] = UNEXPLORED_CELL;
                    }
                }
            }
//            System.out.println(totalMines); // TEST
            updateMineCounter();
        } while (mines != totalMines || gameBoard[initRow - INDEX_SHIFT][initCol - INDEX_SHIFT] == MINE);
    }

    // helper function for generateGameBoard(). Iterates through all cells; if cell is UNEXPLORED_CELL, then update to number if next to mine (cardinal directions and diagonals, 8 possibilities)
    private void updateMineCounter() {
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (gameBoard[i][j] == UNEXPLORED_CELL) {
                    countMines(i,j);
                }
            }
        }
    }

    // helper function for updateMineCounter(). Counts number of mines around a cell. There are eight possible positions UL, U, UR, R, LR, L, LL, L
    private int countMines(int row, int col) {
        int neighborMines = 0;
        // check upper left
        if (row >= MIN_INDEX_TO_BE_REDUCED && col >= MIN_INDEX_TO_BE_REDUCED) {
            if (gameBoard[row - INDEX_SHIFT][col - INDEX_SHIFT] == MINE) {
                neighborMines++;
            }
        }
        // check above
        if (row >= MIN_INDEX_TO_BE_REDUCED) {
            if (gameBoard[row - INDEX_SHIFT][col] == MINE) {
                neighborMines++;
            }
        }
        // check upper right
        if (row >= MIN_INDEX_TO_BE_REDUCED && col <= MAX_INDEX_TO_BE_INCREASED) {
            if (gameBoard[row - INDEX_SHIFT][col + INDEX_SHIFT] == MINE) {
                neighborMines++;
            }
        }
        // check right
        if (col <= MAX_INDEX_TO_BE_INCREASED) {
            if (gameBoard[row][col + INDEX_SHIFT] == MINE) {
                neighborMines++;
            }
        }
        // check below right
        if (row <= MAX_INDEX_TO_BE_INCREASED && col <= MAX_INDEX_TO_BE_INCREASED) {
            if (gameBoard[row + INDEX_SHIFT][col + INDEX_SHIFT] == MINE) {
                neighborMines++;
            }
        }
        // check below
        if (row <= MAX_INDEX_TO_BE_INCREASED) {
            if (gameBoard[row + INDEX_SHIFT][col] == MINE) {
                neighborMines++;
            }
        }
        // check below left
        if (row <= MAX_INDEX_TO_BE_INCREASED && col >= MIN_INDEX_TO_BE_REDUCED) {
            if (gameBoard[row + INDEX_SHIFT][col - INDEX_SHIFT] == MINE) {
                neighborMines++;
            }
        }
        // check left
        if (col >= MIN_INDEX_TO_BE_REDUCED) {
            if (gameBoard[row][col - INDEX_SHIFT] == MINE) {
                neighborMines++;
            }
        }
        if (neighborMines > 0) {
            gameBoard[row][col] = (char)(neighborMines + '0');
        }
        return neighborMines;
    }

    // marks a cell as a mine during game; returns true if valid selection
    public boolean setMark(int row, int col) {

        if (markedGameBoard[row - INDEX_SHIFT][col - INDEX_SHIFT] == MINE) {
            markedGameBoard[row - INDEX_SHIFT][col - INDEX_SHIFT] = MARK;
            markedMines++;
            marks++;
            return true;
        } else if (markedGameBoard[row - INDEX_SHIFT][col - INDEX_SHIFT] == UNEXPLORED_CELL) {
            markedGameBoard[row - INDEX_SHIFT][col - INDEX_SHIFT] = MARK;
            marks++;
            return true;
        } else if (markedGameBoard[row - INDEX_SHIFT][col - INDEX_SHIFT] == MARK) {
            if (gameBoard[row - INDEX_SHIFT][col - INDEX_SHIFT] == MINE) {
                markedGameBoard[row - INDEX_SHIFT][col - INDEX_SHIFT] = MINE;
                markedMines--;
                marks--;
                return true;
            } else {
                markedGameBoard[row - INDEX_SHIFT][col - INDEX_SHIFT] = UNEXPLORED_CELL;
                marks--;
                return true;
            }
        } else { // field is a number or explored cell
            return false;
        }
    }

    public boolean exploreCells (int row, int col) {
        row = row - INDEX_SHIFT;
        col = col - INDEX_SHIFT;
        if (markedGameBoard[row][col] == MINE) { // frees a mine
            hitMine = true;
            return true;
        } else if (markedGameBoard[row][col] == UNEXPLORED_CELL || markedGameBoard[row][col] == MARK) {
            if (gameBoard[row][col] != MINE && gameBoard[row][col] != UNEXPLORED_CELL) {
                markedGameBoard[row][col] = gameBoard[row][col];
                return true;
            } else {
                exploreCellsRecursive(row, col);
                // force all corners to 1 if there is an unexplored cell there
                handleEdgeCases();
                return true;
            }
        }
        return false;
    }

    // iterates through all explored cells and converts the corner into a 1 if it is an unexplored cell
    // This is to handle the corner bug as I cannot replicate here.
    private void handleEdgeCases() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (markedGameBoard[row][col] == EXPLORED_CELL) {
                    // check upper right
                    if (row >= MIN_INDEX_TO_BE_REDUCED && col <= MAX_INDEX_TO_BE_INCREASED) {
                        if (markedGameBoard[row - INDEX_SHIFT][col + INDEX_SHIFT] == UNEXPLORED_CELL) {
                            markedGameBoard[row - INDEX_SHIFT][col + INDEX_SHIFT] = '1';
                        }
                    }
                    // check lower right
                    if (row <= MAX_INDEX_TO_BE_INCREASED && col <= MAX_INDEX_TO_BE_INCREASED) {
                        if (markedGameBoard[row + INDEX_SHIFT][col + INDEX_SHIFT] == UNEXPLORED_CELL) {
                            markedGameBoard[row + INDEX_SHIFT][col + INDEX_SHIFT] = '1';
                        }
                    }
                    // check lower left
                    if (row <= MAX_INDEX_TO_BE_INCREASED && col >= MIN_INDEX_TO_BE_REDUCED) {
                        if (markedGameBoard[row + INDEX_SHIFT][col - INDEX_SHIFT] == UNEXPLORED_CELL) {
                            markedGameBoard[row + INDEX_SHIFT][col - INDEX_SHIFT] = '/';
                        }
                    }
                    // check upper left
                    if (row >= MIN_INDEX_TO_BE_REDUCED && col >= MIN_INDEX_TO_BE_REDUCED) {
                        if (markedGameBoard[row - INDEX_SHIFT][col - INDEX_SHIFT] == UNEXPLORED_CELL) {
                            markedGameBoard[row - INDEX_SHIFT][col - INDEX_SHIFT] = '1';
                        }
                    }
                }
            }
        }
    }

    private void exploreCellsRecursive (int row, int col) {
        markedGameBoard[row][col] = EXPLORED_CELL;
        // check all 8 adjacent cells for numbers in gameBoard
        // check upper left
        if (row >= MIN_INDEX_TO_BE_REDUCED && col >= MIN_INDEX_TO_BE_REDUCED) {
            if (gameBoard[row - INDEX_SHIFT][col - INDEX_SHIFT] != UNEXPLORED_CELL) {
                markedGameBoard[row - INDEX_SHIFT][col - INDEX_SHIFT] = gameBoard[row - INDEX_SHIFT][col - INDEX_SHIFT];
            }
        }
        // check above
        if (row >= MIN_INDEX_TO_BE_REDUCED) {
            if (gameBoard[row - INDEX_SHIFT][col] != UNEXPLORED_CELL) {
                markedGameBoard[row - INDEX_SHIFT][col] = gameBoard[row - INDEX_SHIFT][col];
            }
        }
        // check upper right
        if (row >= MIN_INDEX_TO_BE_REDUCED && col <= MAX_INDEX_TO_BE_INCREASED) {
            if (gameBoard[row - INDEX_SHIFT][col + INDEX_SHIFT] != UNEXPLORED_CELL) {
                markedGameBoard[row - INDEX_SHIFT][col + INDEX_SHIFT] = gameBoard[row - INDEX_SHIFT][col + INDEX_SHIFT];
            }
        }
        // check right
        if (col <= MAX_INDEX_TO_BE_INCREASED) {
            if (gameBoard[row][col + INDEX_SHIFT] != UNEXPLORED_CELL) {
                markedGameBoard[row][col + INDEX_SHIFT] = gameBoard[row][col + INDEX_SHIFT];
            }
        }
        // check below right
        if (row <= MAX_INDEX_TO_BE_INCREASED && col <= MAX_INDEX_TO_BE_INCREASED) {
            if (gameBoard[row + INDEX_SHIFT][col + INDEX_SHIFT] != UNEXPLORED_CELL) {
                markedGameBoard[row + INDEX_SHIFT][col + INDEX_SHIFT] = gameBoard[row + INDEX_SHIFT][col + INDEX_SHIFT];
            }
        }
        // check below
        if (row <= MAX_INDEX_TO_BE_INCREASED) {
            if (gameBoard[row + INDEX_SHIFT][col] != UNEXPLORED_CELL) {
                markedGameBoard[row + INDEX_SHIFT][col] = gameBoard[row + INDEX_SHIFT][col];
            }
        }
        // check below left
        if (row <= MAX_INDEX_TO_BE_INCREASED && col >= MIN_INDEX_TO_BE_REDUCED) {
            if (gameBoard[row + INDEX_SHIFT][col - INDEX_SHIFT] != UNEXPLORED_CELL) {
                markedGameBoard[row + INDEX_SHIFT][col - INDEX_SHIFT] = gameBoard[row + INDEX_SHIFT][col - INDEX_SHIFT];
            }
        }
        // check left
        if (col >= MIN_INDEX_TO_BE_REDUCED) {
            if (gameBoard[row][col - INDEX_SHIFT] != UNEXPLORED_CELL) {
                markedGameBoard[row][col - INDEX_SHIFT] = gameBoard[row][col - INDEX_SHIFT];
            }
        }
        // check four cardinal locations for unexplored cells
        // explore above if in row 2 or below
        if (row >= 1) {
            if (markedGameBoard[row - 1][col] == UNEXPLORED_CELL || markedGameBoard[row - 1][col] == MARK) {
                exploreCellsRecursive(row - 1, col);
            }
        }
        // explore right if in col 8 or below
        if (col <= 7) {
            if (markedGameBoard[row][col + 1] == UNEXPLORED_CELL || markedGameBoard[row][col + 1] == MARK) {
                exploreCellsRecursive(row, col + 1);
            }
        }
        // explore below if in row 8 or below
        if (row <= 7) {
            if (markedGameBoard[row + 1][col] == UNEXPLORED_CELL || markedGameBoard[row + 1][col] == MARK) {
                exploreCellsRecursive(row + 1, col);
            }
        }
        // explore left if in row 1 or below
        if (col >= 1) {
            if (markedGameBoard[row][col - 1] == UNEXPLORED_CELL || markedGameBoard[row][col - 1] == MARK) {
                exploreCellsRecursive(row, col - 1);
            }
        }
    }

    public char[][] getGameBoard() {
        return gameBoard;
    }

    public char[][] getMarkedGameBoard() {
        return markedGameBoard;
    }

    public char[][] getFinalGameBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (gameBoard[i][j] == MINE) {
                    markedGameBoard[i][j] = MINE;
                }
            }
        }
        return markedGameBoard;
    }

    private void countUnexploredCells () {
        for (char[] array:markedGameBoard) {
            for (char i:array) {
                if (i == UNEXPLORED_CELL) {
                    unexploredCells++;
                }
            }
        }
    }

    public int getUnexploredCells() {
        unexploredCells = 0;
        countUnexploredCells();
        return unexploredCells;
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

    public boolean isHitMine() {
        return hitMine;
    }
}
