package com.harsh;

import com.harsh.Models.*;
import com.harsh.controller.GameController;
import com.harsh.exceptions.InvalidMoveException;

import java.util.List;

/**
 * Main entry point for TicTacToe Console Application
 * Supports --smoke-test mode for automated testing in CI/CD pipelines
 */
public class Main {
    public static void main(String[] args) throws InvalidMoveException {
        // Check for smoke test mode
        if (args.length > 0 && "--smoke-test".equals(args[0])) {
            runSmokeTest();
            System.exit(0);
        }

        // Normal game execution
        runNormalGame();
    }

    /**
     * Smoke test mode - runs an automated game for CI/CD validation
     * This ensures the application can start, run, and complete without errors
     */
    private static void runSmokeTest() {
        try {
            System.out.println("Running smoke test...");
            GameController gameController = new GameController();
            int dimension = 3;
            
            // Create two bots for automated play
            List<Player> players = List.of(
                    new Bot("Bot1", new Symbol('X'), PlayerType.BOT, BotDifficultyLevel.EASY),
                    new Bot("Bot2", new Symbol('O'), PlayerType.BOT, BotDifficultyLevel.EASY)
            );
            
            Game game = gameController.startGame(dimension, players);
            
            // Play until game ends (max 9 moves for 3x3 board)
            int maxMoves = dimension * dimension;
            int moveCount = 0;
            
            while (game.getGameState().equals(GameState.InProgress) && moveCount < maxMoves) {
                gameController.makeMove(game);
                moveCount++;
            }
            
            // Verify game completed
            if (game.getGameState().equals(GameState.ENDED) || game.getGameState().equals(GameState.DRAW)) {
                System.out.println("APP_OK");
                System.out.println("Smoke test passed: Game completed successfully");
                if (game.getGameState().equals(GameState.ENDED)) {
                    System.out.println("Winner: " + gameController.getWinner(game).getName());
                } else {
                    System.out.println("Game ended in a draw");
                }
            } else {
                System.err.println("Smoke test failed: Game did not complete");
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Smoke test failed with exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Normal game execution with human player
     */
    private static void runNormalGame() throws InvalidMoveException {
        System.out.println("Welcome to TicTacToe!");

        GameController gameController = new GameController();
        int dimension = 3;
        List<Player> players = List.of(
                new Player("Player1", new Symbol('X'), PlayerType.HUMAN),
                new Bot("Bot", new Symbol('O'), PlayerType.BOT, BotDifficultyLevel.EASY)
        );
        Game game = gameController.startGame(dimension, players);

        while (game.getGameState().equals(GameState.InProgress)) {
            gameController.printBoard(game);
            gameController.makeMove(game);
        }
        
        gameController.printBoard(game);
        if (gameController.checkState(game).equals(GameState.ENDED)) {
            System.out.println("The winner is " + gameController.getWinner(game).getName());
        } else {
            System.out.println("It is a Draw");
        }
    }
}