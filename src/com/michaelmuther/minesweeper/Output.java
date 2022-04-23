package com.michaelmuther.minesweeper;

public class Output {

    public Output(char[][] gameBoard){
        for (char[] array:gameBoard) {
            for (char field:array) {
                System.out.print(field);
            }
            System.out.println();
        }
    }

}
