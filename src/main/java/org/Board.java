package org;

import java.util.Random;

public class Board {

    private static int SIZE = 4;

    private Integer[][] board = new Integer[SIZE][SIZE];

    public Board(){
        Random random = new Random();
        for (int i = 0; i < SIZE; ++i){
            for (int j = 0; j < SIZE; ++j){
                board[i][j] = random.nextBoolean()? null : 2;
            }
        }
    }

    public Board(Board board){
        for (int i = 0; i < SIZE; ++i)
            for (int j = 0; j < SIZE; ++j){
                this.board[i][j] = board.getBoard()[i][j];
            }
    }

    public Integer[][] getBoard(){
        return board;
    }

    public void setBoard(Integer[][] board){
        this.board = board;
    }

    public boolean isEqual(Board board){
        for (int i = 0; i < SIZE; ++i)
            for (int j = 0; j < SIZE; ++j){
                if (this.board[i][j] != board.getBoard()[i][j]){
                    return false;
                }
            }
        return true;
    }

    public void printBoard(){
        System.out.println("[");
        for (int i = 0; i < SIZE; ++i){
            System.out.print("\t[");
            for (int j = 0; j < SIZE; ++j){
                System.out.print(board[i][j] != null? board[i][j]: " ");  // Useful for debugging
//                System.out.print(board[i][j]);
                if (j < SIZE - 1){
                    System.out.print(", ");
                }
            }
            System.out.print("]");
            if (i < SIZE - 1){
                System.out.print(", ");
            }
            System.out.println();
        }
        System.out.print("]");
    }

    public boolean isEndGame(){
        if (!isBoardFull()){
            return false;
        }
        // Find 2048 in the board
        for (int i = 0; i < SIZE; ++i)
            for (int j = 0; j < SIZE; ++j)
                if (board[i][j] != null && board[i][j].intValue() == 2048)
                    return true;

        // Create an identical board and try to merge it in different directions.
        // If the result board is the same for all directions, then it means End Game is reached.
        Board newBoard = new Board(this);
        newBoard.mergeLeft(true);
        newBoard.mergeUp(true);
        newBoard.mergeDown(true);
        newBoard.mergeUp(true);
        if (!this.isEqual(newBoard)){
            return false;
        }
        return true;
    }

    protected boolean isBoardFull(){
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (board[i][j] == null){
                    return false;
                }
            }
        }
        return true;
    }

    public void mergeLeft(boolean addNumberEnabled){
        Board before = new Board(this);

        // Step 1: For each cell, check if there are any cells that can be merged
        for (int i = 0; i < SIZE; ++i)
            for (int j = 0; j < SIZE; ++j)
                if (board[i][j] != null)
                    for (int k = j + 1; k < SIZE; ++k)
                        if (board[i][k] != null && board[i][j].intValue() == board[i][k].intValue()){
                            // can be merged
                            board[i][j] += board[i][k];
                            board[i][k] = null;
                            break;
                        } else {
                            // cannot be merged. If it is null, then check the next cell too.
                            if (board[i][k] != null){
                                break;
                            }
                        }

        // Step 2: Remove leading null values from the left
        for (int i = 0; i < SIZE; ++i)
            for (int j = 0; j < SIZE; ++j)
                for (int k = j + 1 ; k < SIZE; ++k)
                    if (board[i][j] == null && board[i][k] != null){
                        board[i][j] = board[i][k];
                        board[i][k] = null;
                        break;
                    }

        // Step 3: If there is a move, generate a 2 or 4 at random empty spaces after each merge
        if (!before.isEqual(this) && addNumberEnabled)
            addRandomNumber();
    }

    public void mergeRight(boolean addNumberEnabled){
        // Similar to mergeLeft() but loop from the last column
        Board before = new Board(this);

        // Step 1: For each cell, check if there are any cells that can be merged
        for (int i = 0; i < SIZE; ++i)
            for (int j = SIZE - 1; j >= 0 ; --j)
                if (board[i][j] != null)
                    for (int k = j - 1; k >= 0; --k)
                        if (board[i][k] != null && board[i][j].intValue() == board[i][k].intValue()){
                            // can be merged
                            board[i][j] += board[i][k];
                            board[i][k] = null;
                            break;
                        } else {
                            // cannot be merged. If it is null, then check the next cell too.
                            if (board[i][k] != null){
                                break;
                            }
                        }

        // Step 2: Remove leading null values from the right
        for (int i = 0; i < SIZE; ++i)
            for (int j = SIZE - 1; j >= 0; --j)
                for (int k = j - 1; k >= 0; --k)
                    if (board[i][j] == null && board[i][k] != null){
                        board[i][j] = board[i][k];
                        board[i][k] = null;
                        break;
                    }

        // Step 3: If there is a move, generate a 2 or 4 at random empty spaces after each merge
        if (!before.isEqual(this) && addNumberEnabled)
            addRandomNumber();
    }

    public void mergeUp(boolean addNumberEnabled){
        Board before = new Board(this);

        // Step 1: For each cell, check if there are any cells that can be merged
        for (int i = 0; i < SIZE; ++i)
            for (int j = 0; j < SIZE; ++j)
                if (board[j][i] != null)
                    for (int k = j + 1; k < SIZE; ++k)
                        if (board[k][i] != null && board[j][i].intValue() == board[k][i].intValue()){
                            // can be merged
                            board[j][i] += board[k][i];
                            board[k][i] = null;
                            break;
                        } else {
                            // cannot be merged. If it is null, then check the next cell too.
                            if (board[k][i] != null){
                                break;
                            }
                        }

        // Step 2: Remove leading null values from the top
        for (int i = 0; i < SIZE; ++i)
            for (int j = 0; j < SIZE; ++j)
                for (int k = j + 1 ; k < SIZE; ++k)
                    if (board[j][i] == null && board[k][i] != null){
                        board[j][i] = board[k][i];
                        board[k][i] = null;
                        break;
                    }

        // Step 3: If there is a move, generate a 2 or 4 at random empty spaces after each merge
        if (!before.isEqual(this) && addNumberEnabled)
            addRandomNumber();
    }

    public void mergeDown(boolean addNumberEnabled){
        Board before = new Board(this);

        // Step 1: For each cell, check if there are any cells that can be merged
        for (int i = 0; i < SIZE; ++i)
            for (int j = SIZE - 1; j >= 0 ; --j)
                if (board[j][i] != null)
                    for (int k = j - 1; k >= 0; --k)
                        if (board[k][i] != null && board[j][i].intValue() == board[k][i].intValue()){
                            // can be merged
                            board[j][i] += board[k][i];
                            board[k][i] = null;
                            break;
                        } else {
                            // cannot be merged. If it is null, then check the next cell too.
                            if (board[k][i] != null){
                                break;
                            }
                        }

        // Step 2: Remove leading null values from the bottom
        for (int i = 0; i < SIZE; ++i)
            for (int j = SIZE - 1; j >= 0; --j)
                for (int k = j - 1; k >= 0; --k)
                    if (board[j][i] == null && board[k][i] != null){
                        board[j][i] = board[k][i];
                        board[k][i] = null;
                        break;
                    }

        // Step 3: If there is a move, generate a 2 or 4 at random empty spaces after each merge
        if (!before.isEqual(this) && addNumberEnabled)
            addRandomNumber();
    }

    public void addRandomNumber(){
        if (!isBoardFull()){
            Random random = new Random();
            while (true){
                int a = random.nextInt(SIZE);
                int b = random.nextInt(SIZE);
                if (board[a][b] == null){
                    board[a][b] = random.nextBoolean()? 2 : 4;
                    break;
                }
            }
        }
    }
}
