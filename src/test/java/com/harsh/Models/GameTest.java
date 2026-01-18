package com.harsh.Models;

import com.harsh.exceptions.InvalidMoveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Unit tests for Game class
 */
class GameTest {
    private Game game;
    private List<Player> players;

    @BeforeEach
    void setUp() {
        players = List.of(
                new Bot("Bot1", new Symbol('X'), PlayerType.BOT, BotDifficultyLevel.EASY),
                new Bot("Bot2", new Symbol('O'), PlayerType.BOT, BotDifficultyLevel.EASY)
        );
        game = new Game(3, players);
    }

    @Test
    void testGameInitialization() {
        assertNotNull(game);
        assertNotNull(game.getBoard());
        assertEquals(3, game.getBoard().getSize());
        assertEquals(2, game.getPlayers().size());
        assertEquals(GameState.InProgress, game.getGameState());
        assertEquals(0, game.getMoves().size());
        assertEquals(0, game.getNextPlayerMoveIndex());
        assertNull(game.getWinner());
    }

    @Test
    void testGameStateChanges() {
        assertEquals(GameState.InProgress, game.getGameState());
        game.setGameState(GameState.DRAW);
        assertEquals(GameState.DRAW, game.getGameState());
    }

    @Test
    void testMakeMove() throws InvalidMoveException {
        int initialMoveCount = game.getMoves().size();
        game.makeMove();
        assertEquals(initialMoveCount + 1, game.getMoves().size());
    }

    @Test
    void testGameCanComplete() throws InvalidMoveException {
        // Make enough moves to potentially end the game
        int maxMoves = 9;
        for (int i = 0; i < maxMoves && game.getGameState() == GameState.InProgress; i++) {
            game.makeMove();
        }
        
        // Game should be in a terminal state
        assertTrue(game.getGameState() == GameState.ENDED || game.getGameState() == GameState.DRAW);
    }
}
