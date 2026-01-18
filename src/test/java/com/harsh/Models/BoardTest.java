package com.harsh.Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Board class
 */
class BoardTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board(3);
    }

    @Test
    void testBoardInitialization() {
        assertNotNull(board);
        assertEquals(3, board.getSize());
        assertNotNull(board.getBoard());
        assertEquals(3, board.getBoard().size());
        assertEquals(3, board.getBoard().get(0).size());
    }

    @Test
    void testAllCellsInitializedAsEmpty() {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                Cell cell = board.getBoard().get(i).get(j);
                assertEquals(CellState.EMPTY, cell.getCellState());
                assertEquals(i, cell.getRow());
                assertEquals(j, cell.getCol());
            }
        }
    }

    @Test
    void testBoardSizeSetter() {
        board.setSize(5);
        assertEquals(5, board.getSize());
    }
}
