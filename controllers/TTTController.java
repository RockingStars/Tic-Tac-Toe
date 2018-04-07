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

        _view.setStatus(_model.getTurnMessage(currentPlayer));
    }

    @Override
    public Node getView() {
        return _view.getNode();
    }

    @Override
    public void doPlayerMove(int x, int y) {
        if (_model.isValidMove(x, y)) {
            if (currentPlayer == player1) {
                CommandExecutor.execute(new MoveCommand(ServerConnection.getInstance(), x * y));
                // @todo Error handling
            }

            _model.setPlayerAtPosition(currentPlayer, x, y);
            _view.setCellImage(x, y);

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

            switchPlayers();
            _view.setStatus(_model.getTurnMessage(currentPlayer));
        } else {
            _view.setStatus("Invalid move");
        }
        randomGenerator();
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
        doPlayerMove(position / 3, position % 3);
    }
}
