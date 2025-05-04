package org;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardTest {

    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();
    }

    @Test
    public void testInitBoard() {
        Integer[][] initialBoard = board.getBoard();
        boolean isValid = true;

        // Check if all cells are either null or 2
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (initialBoard[i][j] != null && initialBoard[i][j] != 2) {
                    isValid = false;
                    break;
                }
            }
        }
        assertTrue(isValid, "Initial board should only contain null or 2.");
    }

    @Test
    public void testCopyBoard() {
        Board copy = new Board(board);
        assertTrue(board.isEqual(copy), "The copied board should be equal to the original.");
    }

    @Test
    public void testMergeLeft() {
        Integer[][] testBoard = {
                {2, 2, null, null},
                {2, 2, null, null},
                {2, null, 2, 2},
                {null, null, null, 2}
        };
        board = new Board();
        board.setBoard(testBoard);
        board.mergeLeft(false);

        Integer[][] result = board.getBoard();

        assertTrue(Arrays.equals(result[0], new Integer[]{4, null, null, null}));;
        assertTrue(Arrays.equals(result[1], new Integer[]{4, null, null, null}));
        assertTrue(Arrays.equals(result[2], new Integer[]{4, 2, null, null}));
        assertTrue(Arrays.equals(result[3], new Integer[]{2, null, null, null}));
    }

    @Test
    public void testMergeRight() {
        Integer[][] testBoard = {
                {2, 2, null, null},
                {2, 2, null, null},
                {2, null, 2, 2},
                {null, null, null, 2}
        };
        board = new Board();
        board.setBoard(testBoard);
        board.mergeRight(false);

        Integer[][] result = board.getBoard();

        assertTrue(Arrays.equals(result[0], new Integer[]{null, null, null, 4}));;
        assertTrue(Arrays.equals(result[1], new Integer[]{null, null, null, 4}));
        assertTrue(Arrays.equals(result[2], new Integer[]{null, null, 2, 4}));
        assertTrue(Arrays.equals(result[3], new Integer[]{null, null, null, 2}));
    }

    @Test
    public void testMergeUp() {
        Integer[][] testBoard = {
                {2, 2, null, null},
                {2, 2, null, null},
                {2, null, 2, 2},
                {null, null, null, 2}
        };
        board = new Board();
        board.setBoard(testBoard);
        board.mergeUp(false);

        Integer[][] result = board.getBoard();

        assertTrue(Arrays.equals(result[0], new Integer[]{4, 4, 2, 4}));;
        assertTrue(Arrays.equals(result[1], new Integer[]{2, null, null, null}));
        assertTrue(Arrays.equals(result[2], new Integer[]{null, null, null, null}));
        assertTrue(Arrays.equals(result[3], new Integer[]{null, null, null, null}));
    }

    @Test
    public void testMergeDown() {
        Integer[][] testBoard = {
                {2, 2, null, null},
                {2, 2, null, null},
                {2, null, 2, 2},
                {null, null, null, 2}
        };
        board = new Board();
        board.setBoard(testBoard);
        board.mergeDown(false);

        Integer[][] result = board.getBoard();

        assertTrue(Arrays.equals(result[0], new Integer[]{null, null, null, null}));;
        assertTrue(Arrays.equals(result[1], new Integer[]{null, null, null, null}));
        assertTrue(Arrays.equals(result[2], new Integer[]{2, null, null, null}));
        assertTrue(Arrays.equals(result[3], new Integer[]{4, 4, 2, 4}));
    }

    @Test
    public void testIsEndGame() {
        // Set up a board that is full and has no possible merges
        Integer[][] fullBoard = {
                {2, 4, 2, 4},
                {4, 2, 4, 2},
                {8, 4, 16, 4},
                {32, 64, 128, 256}
        };
        board = new Board();
        board.setBoard(fullBoard);
        assertTrue(board.isEndGame());
    }

    @Test
    public void testIsEqual() {
        // Test if two identical boards are considered equal
        Board copy = new Board(board);
        assertTrue(board.isEqual(copy));

        // Modify the board
        board.mergeLeft(false);
        assertFalse(board.isEqual(copy));
    }
}
