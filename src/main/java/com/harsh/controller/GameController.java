package com.harsh.controller;

import com.harsh.Models.Game;
import com.harsh.Models.GameState;
import com.harsh.Models.Move;
import com.harsh.Models.Player;
import com.harsh.exceptions.InvalidMoveException;

import java.util.List;

public class GameController {
    public Game startGame(int dimension, List<Player> players){
        //Validate Game : two player must have different symbols otherwise throw exception

        return new Game(dimension, players);
    }

    public void makeMove(Game game) throws InvalidMoveException {
        game.makeMove();
    }

    public GameState checkState(Game game){
        return game.getGameState();
    }

    public Player getWinner(Game game){
        return game.getWinner();
    }

    public void printBoard(Game game){
        game.printBoard();
    }
}
