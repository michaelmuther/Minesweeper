package com.michaelmuther.minesweeper;

public class Main {

    public static void main(String[] args) {
        Input i = new Input();
        GameBoard g = new GameBoard(i.getMines());
        new Output(g.getGameBoard());
    }

}
