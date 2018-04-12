package com.rockingstar.modules.TicTacToe.controllers;

import com.rockingstar.engine.ServerConnection;
import com.rockingstar.engine.command.client.CommandExecutor;
import com.rockingstar.engine.command.client.MoveCommand;
import com.rockingstar.engine.game.AbstractGame;
import com.rockingstar.engine.game.Player;
import com.rockingstar.engine.game.State;
import com.rockingstar.modules.TicTacToe.models.TTTModel;
import com.rockingstar.modules.TicTacToe.views.TTTView;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Random;

public class TTTController extends AbstractGame {

    private TTTModel _model;
    private TTTView _view;

    public TTTController(Player player1, Player player2) {
        super(player1, player2);

        this.player1.setCharacter('x');
        this.player2.setCharacter('o');

        _view = new TTTView(this);
        _model = new TTTModel(_view);

        _model.addEventHandlers();
        _model.createCells();

        _view.setBoard(_model.getBoard());
        _view.generateBoardVisual();
    }

    @Override
    public Node getView() {
        return _view.getNode();
    }

    @Override
    public void doPlayerMove(int x, int y) {
        if (!gameFinished()) {
            if (_model.isValidMove(x, y)) {
                if (yourTurn) {
                    CommandExecutor.execute(new MoveCommand(ServerConnection.getInstance(), y * 3 + x));

                    _model.setPlayerAtPosition(player1, x, y);
                    _view.setCellImage(x, y);

                    _view.setStatus("Opponent's turn");
                    yourTurn = false;
                }
                else
                    _view.setErrorStatus("It's not your turn");
            }
            else
                _view.setErrorStatus("Invalid move");
        }

        /*if (currentPlayer == player2) {
            randomGenerator();
        }*/
    }

    @Override
    public void doPlayerMove(int position) {
        if (!gameFinished()) {
            if (yourTurn)
                return;

            int x = position % 3;
            int y = position / 3;

            _model.setPlayerAtPosition(player2, x, y);
            _view.setCellImage(x, y);

            yourTurn = true;
        }
    }

    public void randomGenerator() {
        Random rand = new Random();

        int tempX = 3;
        int tempY = 3;

        while (!_model.isValidMove(tempX, tempY)) {
            tempX = rand.nextInt(2 + 1);
            tempY = rand.nextInt(2 + 1);
        }

        doPlayerMove(tempX, tempY);
    }

    @Override
    public void showPossibleMoves() {}

    @Override
    public void doYourTurn() {
        _view.setStatus("It's your turn!");
        yourTurn = true;
    }

    private boolean gameFinished() {
        if (_model.hasWon(player1) || _model.hasWon(player2)) {
            _view.setStatus("Player " + (yourTurn ? player1 : player2).getUsername() + " has won! Congratulations.");

            setGameState(State.GAME_FINISHED);
            _view.setIsFinished(true);

            return true;
        }

        return _model.isFull();
    }

    @Override
    public void gameEnded(String result) {
        super.gameEnded(result);
        _view.setIsFinished(true);

        if(!_model.isFull())
            _view.setStatus("Player " + (yourTurn ? player1 : player2).getUsername() + " has won! Congratulations.");
        else
            _view.setStatus("It's a draw! N00bs");

        setGameState(State.GAME_FINISHED);

        Platform.runLater(() -> {
            Alert returnToLobby = new Alert(Alert.AlertType.CONFIRMATION);
            returnToLobby.setTitle("Game ended!");
            returnToLobby.setHeaderText(null);
            returnToLobby.setContentText("Do you want to return to the lobby?");

            returnToLobby.showAndWait();

            if (returnToLobby.getResult() == ButtonType.OK)
                toLobby();
        });
    }
}
