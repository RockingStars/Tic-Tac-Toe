package com.rockingstar.modules.TicTacToe.models;

import com.rockingstar.modules.TicTacToe.views.TTTView;
import javafx.scene.control.Label;
import javafx.application.Platform;

public class TTTModel {

    public char whoseTurn = 'X';

    public Cell[][] cell = new Cell[3][3];

    public Label status = new Label("X's turn to play");

    public void addEventHandlers(TTTView view) {
        view.getEndButton().setOnAction(e -> Platform.exit());
        view.getNewGameButton().setOnAction(e -> {
            cell = new Cell[3][3];

            status.setText(whoseTurn + "'s turn.");

            view.setBoard(cell);
            view.generateBoardVisual();
        });
    }

    public Cell[][] getBoard() {
        return cell;
    }
}
