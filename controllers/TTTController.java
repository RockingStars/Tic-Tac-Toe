package com.rockingstar.modules.TicTacToe.controllers;

import com.rockingstar.engine.ServerConnection;
import com.rockingstar.engine.command.client.CommandExecutor;
import com.rockingstar.engine.command.client.MoveCommand;
import com.rockingstar.engine.game.AbstractGame;
import com.rockingstar.engine.game.Player;
import com.rockingstar.modules.TicTacToe.models.TTTModel;
import com.rockingstar.modules.TicTacToe.views.TTTView;
import javafx.scene.Node;
import java.util.Random;

public class TTTController extends AbstractGame {

    private TTTModel _model;
    private TTTView _view;

    private Player[][] _board;

    public TTTController(Player player1, Player player2) {
        super(player1, player2);

        this.player1.setCharacter('x');
        this.player2.setCharacter('o');

        _view = new TTTView(this);
        _model = new TTTModel(_view);

        _board = _model.getBoard();
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
        if (_model.hasWon(currentPlayer)) {
            _view.setStatus("Player " + currentPlayer.getUsername() + " has won! Congratulations.");
            _view.setIsFinished(true);
            return;
        }
        else if (_model.isFull()) {
            _view.setStatus("Neither player has won. Too bad.");
            _view.setIsFinished(true);
            return;
        }

        if (_model.isValidMove(x, y)) {
            if (yourTurn) {
                CommandExecutor.execute(new MoveCommand(ServerConnection.getInstance(), y * 3 + x));
                _model.setPlayerAtPosition(currentPlayer, x, y);
                _view.setCellImage(x, y);
                yourTurn = false;
                setCurrentPlayer(1);
            }
            else {
                _view.setErrorStatus("It's not your turn");
            }

        }
        else
            _view.setErrorStatus("Invalid move");

        if (currentPlayer == player2) {
            randomGenerator();
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
    public void doPlayerMove(int position) {
        int x = position / 3;
        int y = position % 3;

        _model.setPlayerAtPosition(currentPlayer, x, y);
        _view.setCellImage(x, y);
        setCurrentPlayer(0);
    }


    public boolean getIsYourTurn() {
        return yourTurn;
    }

    @Override
    public void setCurrentPlayer(int id) {
        currentPlayer = id == 0 ? player1 : player2;
        _view.setStatus(_model.getTurnMessage(currentPlayer));
    }

}
