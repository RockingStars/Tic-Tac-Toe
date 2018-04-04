package com.rockingstar.modules.TicTacToe.controllers;

import com.rockingstar.engine.game.AbstractGame;
import com.rockingstar.engine.game.models.Player;
import com.rockingstar.modules.TicTacToe.models.Cell;
import com.rockingstar.modules.TicTacToe.models.TTTModel;
import com.rockingstar.modules.TicTacToe.views.TTTView;
import javafx.scene.Node;

public class TTTController extends AbstractGame {

    private TTTModel _model;
    private TTTView _view;

    private Cell[][] _board;

    public TTTController() {
        _model = new TTTModel();
        _view = new TTTView();

        _board = _model.getBoard();
        _model.addEventHandlers(_view);

        _view.setBoard(_model.getBoard());
        _view.generateBoardVisual();
    }

    @Override
    public Node getView() {
        return _view.getNode();
    }

    /** Determine if the cell are all occupied */
    public boolean hasWon(Player player) {
        for (int i = 0; i < 3; i++)
            if (_board[i][0].getPlayer() == player
                    && _board[i][1].getPlayer() == player
                    && _board[i][2].getPlayer() == player) {
                return true;
            }

        for (int j = 0; j < 3; j++)
            if (_board[0][j].getPlayer() == player
                    && _board[1][j].getPlayer() == player
                    && _board[2][j].getPlayer() == player) {
                return true;
            }

        if (_board[0][0].getPlayer() == player
                && _board[1][1].getPlayer() == player
                && _board[2][2].getPlayer() == player) {
            return true;
        }

        if (_board[0][2].getPlayer() == player
                && _board[1][1].getPlayer() == player
                && _board[2][0].getPlayer() == player) {
            return true;
        }

        return false;
    }
}
