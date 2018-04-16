/*
 * Enjun
 *
 * @version     1.0 Beta 1
 * @author      Rocking Stars
 * @copyright   2018, Enjun
 *
 * Copyright 2018 RockingStars

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

/**
 * This class sets up Tic Tac Toe.
 * @author Rocking Stars
 * @since 1.0 Beta 1
 */
public class TTTController extends AbstractGame {

    /**
     * The TicTacToe model, used for determining things like if there is a winner (and if so, who)
     */
    private TTTModel _model;

    /**
     * The TicTacToe view, which handles the graphical part of things
     */
    private TTTView _view;

    /**
     * Constructor of TTTController
     * @param player1 The local player
     * @param player2 The opponent (a player on a different client)
     */
    public TTTController(Player player1, Player player2) {
        super(player1, player2);

        this.player1.setCharacter('x');
        this.player2.setCharacter('o');

        _view = new TTTView(this);
        _model = new TTTModel(_view);

        _view.getEndButton().setOnAction(e -> Platform.exit());
        _view.getNewGameButton().setOnAction(e -> _model.clearBoard());
        _model.createCells();

        _view.setBoard(_model.getBoard());
        _view.generateBoardVisual();
    }

    /**
     * Returns the current view
     * @return The current view
     */
    @Override
    public Node getView() {
        return _view.getNode();
    }

    /**
     * Called after a local player has clicked on a field
     * @param x The x coordinate
     * @param y The y coordinate
     */
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
    }

    /**
     * Called after the opponent has made a move
     * @param position The position
     */
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

    /**
     * Tells the user it's their turn and sets yourTurn to true
     */
    @Override
    public void doYourTurn() {
        _view.setStatus("It's your turn!");
        yourTurn = true;
    }

    /**
     * If a player has won, or if the board is full, the game state should be set to finished
     * @return Whether or not the game has ended
     */
    private boolean gameFinished() {
        if (_model.hasWon(player1) || _model.hasWon(player2)) {
            _view.setStatus("Player " + (yourTurn ? player1 : player2).getUsername() + " has won! Congratulations.");

            setGameState(State.GAME_FINISHED);
            _view.setIsFinished(true);

            return true;
        }

        return _model.isFull();
    }

    /**
     * Updates the status when the game has ended.
     * @param result The exact status
     */
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
            returnToLobby.setHeaderText("The game has ended.");
            returnToLobby.setContentText("Do you want to return to the lobby?");

            returnToLobby.showAndWait();

            if (returnToLobby.getResult() == ButtonType.OK)
                toLobby();
        });
    }
}
