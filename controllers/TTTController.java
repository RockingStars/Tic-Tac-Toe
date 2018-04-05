package com.rockingstar.modules.TicTacToe.controllers;

import com.rockingstar.engine.game.AbstractGame;
import com.rockingstar.engine.game.models.Player;
import com.rockingstar.modules.TicTacToe.models.TTTModel;
import com.rockingstar.modules.TicTacToe.views.TTTView;
import javafx.scene.Node;

public class TTTController extends AbstractGame {

    private TTTModel _model;
    private TTTView _view;

    private Player[][] _board;

    public TTTController(Player player1, Player player2) {
        super(player1, player2);

        player1.setCharacter('x');
        player2.setCharacter('o');

        _model = new TTTModel();
        _view = new TTTView(this);

        _board = _model.getBoard();
        _model.addEventHandlers(_view);
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
        if (_model.isValidMove(x, y)) {
            _model.setPlayerAtPosition(currentPlayer, x, y);
            _view.setCellImage(x, y);
            switchPlayers();
        }
        else
            System.out.println("Invalid move");
    }
}
