package com.harsh.strategy;

import com.harsh.Models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for WinningAlgorithm class
 */
class WinningAlgorithmTest {
    private winningAlgorithm algorithm;
    private Board board;

    @BeforeEach
    void setUp() {
        algorithm = new winningAlgorithm();
        board = new Board(3);
    }

    @Test
    void testRowWin() {
        Player player = new Player("TestPlayer", new Symbol('X'), PlayerType.HUMAN);
        
        // Fill first row
        for (int col = 0; col < 3; col++) {
            Cell cell = board.getBoard().get(0).get(col);
            cell.setPlayer(player);
            cell.setCellState(CellState.FILLED);
            Move move = new Move(cell, player);
            
            if (col == 2) {
                // Last move should win
                assertTrue(algorithm.checkWinner(board, move));
            } else {
                assertFalse(algorithm.checkWinner(board, move));
            }
        }
    }

    @Test
    void testColumnWin() {
        Player player = new Player("TestPlayer", new Symbol('O'), PlayerType.HUMAN);
        
        // Fill first column
        for (int row = 0; row < 3; row++) {
            Cell cell = board.getBoard().get(row).get(0);
            cell.setPlayer(player);
            cell.setCellState(CellState.FILLED);
            Move move = new Move(cell, player);
            
            if (row == 2) {
                // Last move should win
                assertTrue(algorithm.checkWinner(board, move));
            } else {
                assertFalse(algorithm.checkWinner(board, move));
            }
        }
    }

    @Test
    void testLeftDiagonalWin() {
        Player player = new Player("TestPlayer", new Symbol('X'), PlayerType.HUMAN);
        
        // Fill left diagonal (0,0), (1,1), (2,2)
        for (int i = 0; i < 3; i++) {
            Cell cell = board.getBoard().get(i).get(i);
            cell.setPlayer(player);
            cell.setCellState(CellState.FILLED);
            Move move = new Move(cell, player);
            
            if (i == 2) {
                // Last move should win
                assertTrue(algorithm.checkWinner(board, move));
            } else {
                assertFalse(algorithm.checkWinner(board, move));
            }
        }
    }

    @Test
    void testRightDiagonalWin() {
        Player player = new Player("TestPlayer", new Symbol('O'), PlayerType.HUMAN);
        
        // Fill right diagonal (0,2), (1,1), (2,0)
        for (int i = 0; i < 3; i++) {
            Cell cell = board.getBoard().get(i).get(2 - i);
            cell.setPlayer(player);
            cell.setCellState(CellState.FILLED);
            Move move = new Move(cell, player);
            
            if (i == 2) {
                // Last move should win
                assertTrue(algorithm.checkWinner(board, move));
            } else {
                assertFalse(algorithm.checkWinner(board, move));
            }
        }
    }

    @Test
    void testNoWin() {
        Player player1 = new Player("Player1", new Symbol('X'), PlayerType.HUMAN);
        Player player2 = new Player("Player2", new Symbol('O'), PlayerType.HUMAN);
        
        // Make some moves that don't create a win
        Cell cell1 = board.getBoard().get(0).get(0);
        cell1.setPlayer(player1);
        cell1.setCellState(CellState.FILLED);
        Move move1 = new Move(cell1, player1);
        assertFalse(algorithm.checkWinner(board, move1));
        
        Cell cell2 = board.getBoard().get(0).get(1);
        cell2.setPlayer(player2);
        cell2.setCellState(CellState.FILLED);
        Move move2 = new Move(cell2, player2);
        assertFalse(algorithm.checkWinner(board, move2));
    }
}
