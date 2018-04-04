package com.rockingstar.modules.TicTacToe.views;

import com.rockingstar.modules.TicTacToe.models.Cell;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;

public class TTTView {

    private BorderPane _borderPane;

    private Button _endButton;
    private Button _newGameButton;

    private GridPane _pane;

    private Cell[][] _board;
    private HBox _buttons;

    private Label _status;

    public TTTView() {
        _borderPane = new BorderPane();
        setup();
    }

    private void setup() {
        _endButton = new Button("End game");
        _newGameButton = new Button("New game");

        _pane = new GridPane();

        _buttons = new HBox();
        _buttons.setSpacing(60.0);
        _buttons.setMinHeight(50);
        _buttons.setAlignment(Pos.CENTER);
        _buttons.getChildren().addAll(_newGameButton, _endButton);

        _borderPane.setCenter(_pane);
        _borderPane.setTop(_buttons);
        _borderPane.setBottom(_status);
    }

    public void generateBoardVisual() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                //_pane.add(_board[i][j] = new Cell(), j, i);
                _pane.add(new Rectangle(20, 20), j, i);
    }

    public Button getEndButton() {
        return _endButton;
    }

    public Button getNewGameButton() {
        return _newGameButton;
    }

    public Node getNode() {
        return _borderPane;
    }

    public void setBoard(Cell[][] board) {
        _board = board;
    }

    public void setStatus(Label status) {
        _status = status;
    }
}
