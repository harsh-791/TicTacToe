package com.harsh.controller;

import com.harsh.Models.*;
import com.harsh.exceptions.InvalidMoveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Unit tests for GameController class
 */
class GameControllerTest {
    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new GameController();
    }

    @Test
    void testStartGame() {
        int dimension = 3;
        List<Player> players = List.of(
                new Player("Player1", new Symbol('X'), PlayerType.HUMAN),
                new Bot("Bot", new Symbol('O'), PlayerType.BOT, BotDifficultyLevel.EASY)
        );
        
        Game game = gameController.startGame(dimension, players);
        
        assertNotNull(game);
        assertEquals(dimension, game.getBoard().getSize());
        assertEquals(2, game.getPlayers().size());
        assertEquals(GameState.InProgress, gameController.checkState(game));
    }

    @Test
    void testCheckState() {
        int dimension = 3;
        List<Player> players = List.of(
                new Bot("Bot1", new Symbol('X'), PlayerType.BOT, BotDifficultyLevel.EASY),
                new Bot("Bot2", new Symbol('O'), PlayerType.BOT, BotDifficultyLevel.EASY)
        );
        
        Game game = gameController.startGame(dimension, players);
        GameState state = gameController.checkState(game);
        assertEquals(GameState.InProgress, state);
    }

    @Test
    void testGetWinner() {
        int dimension = 3;
        List<Player> players = List.of(
                new Bot("Bot1", new Symbol('X'), PlayerType.BOT, BotDifficultyLevel.EASY),
                new Bot("Bot2", new Symbol('O'), PlayerType.BOT, BotDifficultyLevel.EASY)
        );
        
        Game game = gameController.startGame(dimension, players);
        Player winner = gameController.getWinner(game);
        
        // Initially no winner
        assertNull(winner);
    }
}
