package com.michaelmuther.minesweeper;

public class Main {

    public static void main(String[] args) {
        final String NUMBER_HERE = "There is a number here!";
        final String CONGRATULATIONS = "Congratulations! You found all the mines!";
        Input i = new Input();
        GameBoard g = new GameBoard(i.getMines());
        new Output(g.getGameBoard());
        do {
            GameInput gi = new GameInput();
            boolean validInput;
            do {
                validInput = g.setMark(gi.getIndex1(), gi.getIndex2());
                if (!validInput) {
                    System.out.println(NUMBER_HERE);
                }
            } while (!validInput);
            new Output(g.getMarkedGameBoard());
        } while (!(g.getMarkedMines() == g.getMines() && g.getMarkedMines() == g.getMarks()));
        System.out.println(CONGRATULATIONS);
    }
}
