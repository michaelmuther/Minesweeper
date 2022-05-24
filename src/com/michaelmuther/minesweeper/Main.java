package com.michaelmuther.minesweeper;

public class Main {

    public static void main(String[] args) {
        final String NUMBER_HERE = "There is a number here!";
        final String CONGRATULATIONS = "Congratulations! You found all the mines!";
        final String HIT_MINE = "You stepped on a mine and failed!";
        MenuInput mi = new MenuInput();
        new Output(); // prints empty board
        GameInput giOne = new GameInput(); // this is the input that triggers the board
        GameBoard g = new GameBoard(mi.getMines(), giOne.getRow(), giOne.getCol(), giOne.isFreeInput());
        new Output(g.getMarkedGameBoard());
        boolean winTestMarks = false;
        boolean winTestExplored = false;
        boolean loseTestMine = false;
        boolean gameOver = false;
        do { // game loop
            boolean validInput = false;
            do { // turn loop
                GameInput gi = new GameInput();
                if (gi.isFreeInput()) {
                    validInput = g.exploreCells(gi.getRow(), gi.getCol());
                    if (g.isHitMine()) {
                        loseTestMine = true;
                    } else {
                        if (!validInput) {
                            System.out.println(NUMBER_HERE);
                        }
                    }
                } else { // any other option than free; does not need to be "mine"
                    validInput = g.setMark(gi.getRow(), gi.getCol());
                    if (!validInput) {
                        System.out.println(NUMBER_HERE);
                    }
                }
            } while (!validInput); // turn loop
            winTestExplored = g.getUnexploredCells() == 0;
            winTestMarks = g.getMarkedMines() == g.getMines() && g.getMarkedMines() == g.getMarks();
            gameOver = winTestMarks || winTestExplored || loseTestMine;
            if (!gameOver) {
                new Output(g.getMarkedGameBoard());
            }
        } while (!gameOver);
        if (winTestMarks || winTestExplored) {
            System.out.println(CONGRATULATIONS);
        } else {
            System.out.println(HIT_MINE);
        }
        new Output(g.getFinalGameBoard(), true);
    }
}

/**
 * To dos:
 * update print to not show mines (already does this?) and add beginning and final print (Output) DONE
 * update board creation to after first free is chosen DONE
 *  - It does not matter if an unexplored marked cell is created first
 *  - board creation will happen when the first free cell is chosen
 *  - this first free cell will not be a mined cell. (GameInput -> GameBoard)
 *
 *  There are two boards; the marked gameboard and the original gameboard DONE
 *
 * add functionality to game input for free or mine after the cell indices (GameInput -> GameBoard)
 * add algorithm to unmask cells if they are connected to other empty cells (GameBoard)
 *
 *
 *
 * Update game flow on Main.java.
 *  1. Main will ask how many mines
 *  2. Main will print the empty board before it is created DONE
 *  3. The first free will create the board with an empty cell at that spot.  Let's assume that it always starts with a free selection. DONE
 *  Print final board exposing all mines DONE
 */
