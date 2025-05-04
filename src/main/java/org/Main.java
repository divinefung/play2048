package org;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Let's play 2048!");
        System.out.println("Use num pad to play (8=Up, 6=Right, 2=Down, 4=Left). Press 0 to quit");

        Scanner scanner = new Scanner(System.in);
        Board board = new Board();
        board.printBoard();

        while (true){
            String input = scanner.nextLine().toUpperCase();
            if (input.equals("0")) {
                System.out.println("Game Over!");
                break;
            }
            if (input.equals("8") || input.equals("6") || input.equals("2") || input.equals("4")) {
                switch (input) {
                    case "8": board.mergeUp(true); break;
                    case "6": board.mergeRight(true); break;
                    case "2": board.mergeDown(true); break;
                    case "4": board.mergeLeft(true); break;
                }
                board.printBoard();
                if (board.isEndGame()) {
                    System.out.println("Game Over!");
                    break;
                }
            } else {
                System.out.println("Invalid Input - Use num pad to play (8=Up, 6=Right, 2=Down, 4=Left). Press 0 to quit");
            }
        }
    }
}