package com.rockingstar.modules.TicTacToe.models;

import com.rockingstar.engine.game.Player;
import com.rockingstar.modules.TicTacToe.views.TTTView;
import javafx.application.Platform;

public class TTTModel {

    private Player[][] _board = new Player[3][3];

    private TTTView _view;

    public TTTModel(TTTView view) {
        _view = view;
    }

    public void addEventHandlers() {
        _view.getEndButton().setOnAction(e -> Platform.exit());
        _view.getNewGameButton().setOnAction(e -> clearBoard());
    }

    private void clearBoard() {
        _board = new Player[3][3];
        createCells();

        _view.setBoard(_board);
        _view.generateBoardVisual();
        _view.setIsFinished(false);
    }

    public void createCells() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                _board[i][j] = null;
    }

    public Player[][] getBoard() {
        return _board;
    }

    public boolean isValidMove(int x, int y) {
        if (x > 2 || y > 2 || x < 0 || y < 0)
            return false;

        return _board[x][y] == null;
    }

    public void setPlayerAtPosition(Player player, int x, int y) {
        _board[x][y] = player;
    }

    public boolean hasWon(Player player) {
        for (int i = 0; i < 3; i++)
            if (_board[i][0] == player
                    && _board[i][1] == player
                    && _board[i][2] == player) {
                return true;
            }

        for (int j = 0; j < 3; j++)
            if (_board[0][j] == player
                    && _board[1][j] == player
                    && _board[2][j] == player) {
                return true;
            }

        if (_board[0][0] == player
                && _board[1][1] == player
                && _board[2][2] == player) {
            return true;
        }

        if (_board[0][2] == player
                && _board[1][1] == player
                && _board[2][0] == player) {
            return true;
        }

        return false;
    }

    public boolean isFull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (_board[i][j] == null)
                    return false;

        return true;
    }
}
