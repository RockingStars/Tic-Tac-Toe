package com.rockingstar.modules.TicTacToe.models;

import com.rockingstar.engine.game.models.Player;
import com.rockingstar.modules.TicTacToe.views.TTTView;
import javafx.scene.control.Label;
import javafx.application.Platform;

public class TTTModel {

    public Player[][] cell = new Player[3][3];
    private TTTView _view;

    public TTTModel(TTTView view) {
        _view = view;
    }

    public void addEventHandlers() {
        _view.getEndButton().setOnAction(e -> Platform.exit());
        _view.getNewGameButton().setOnAction(e -> clearBoard());
    }

    public void clearBoard() {
        cell = new Player[3][3];
        createCells();

        _view.setBoard(cell);
        _view.generateBoardVisual();
        _view.setIsFinished(false);
    }

    public void createCells() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                cell[i][j] = null;
    }

    public Player[][] getBoard() {
        return cell;
    }

    public boolean isValidMove(int x, int y) {
        if (x > 2 || y > 2 || x < 0 || y < 0)
            return false;

        return cell[x][y] == null;
    }

    public void setPlayerAtPosition(Player player, int x, int y) {
        cell[x][y] = player;
    }

    public boolean hasWon(Player player) {
        for (int i = 0; i < 3; i++)
            if (cell[i][0] == player
                    && cell[i][1] == player
                    && cell[i][2] == player) {
                return true;
            }

        for (int j = 0; j < 3; j++)
            if (cell[0][j] == player
                    && cell[1][j] == player
                    && cell[2][j] == player) {
                return true;
            }

        if (cell[0][0] == player
                && cell[1][1] == player
                && cell[2][2] == player) {
            return true;
        }

        if (cell[0][2] == player
                && cell[1][1] == player
                && cell[2][0] == player) {
            return true;
        }

        return false;
    }

    public boolean isFull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (cell[i][j] == null)
                    return false;

        return true;
    }

    public String getTurnMessage(Player player) {
        return player.getUsername() + ", it's your turn";
    }
}
