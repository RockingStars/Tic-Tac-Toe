package com.rockingstar.modules.TicTacToe.views;

import com.rockingstar.engine.game.Player;
import com.rockingstar.engine.io.models.Util;
import com.rockingstar.modules.TicTacToe.controllers.TTTController;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.URISyntaxException;

public class TTTView {

    private BorderPane _borderPane;

    private Button _endButton;
    private Button _newGameButton;

    private GridPane _pane;

    private Player[][] _board;
    private HBox _buttons;

    private Label _status;
    private Label _errorStatus;

    private TTTController _controller;

    private boolean _isFinished;
    private HBox _labels;

    public TTTView(TTTController controller) {
        _borderPane = new BorderPane();
        _controller = controller;

        _status = new Label();
        _status.setFont(new Font(16));

        _errorStatus = new Label();
        _errorStatus.setFont(new Font(16));
        _errorStatus.setTextFill(Color.RED);


        _isFinished = false;

        setup();
    }

    private void setup() {
        _endButton = new Button("End game");
        _newGameButton = new Button("New game");

        _pane = new GridPane();
        _pane.setAlignment(Pos.CENTER);
        _pane.setPadding(new Insets(20));

        _buttons = new HBox();
        _buttons.setSpacing(60.0);
        _buttons.setMinHeight(50);
        _buttons.setAlignment(Pos.CENTER);
        _buttons.getChildren().addAll(_newGameButton, _endButton);

        _labels = new HBox();
        _labels.setSpacing(60.0);
        _labels.setMinHeight(50);
        _labels.setAlignment(Pos.CENTER);
        _labels.getChildren().addAll(_status, _errorStatus);

        _borderPane.setCenter(_pane);
        _borderPane.setBottom(_buttons);
        _borderPane.setTop(_labels);
    }

    public void generateBoardVisual() {
        _pane.getChildren().clear();

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                setCellImage(i, j);
    }

    public Button getEndButton() {
        return _endButton;
    }

    public void setCellImage(int x, int y) {
        String fileName;
        ImageView imageView = new ImageView();

        try {
            if (_board[x][y] != null) {
                switch (_board[x][y].getCharacter()) {
                    case 'x':
                        fileName = "x.gif";
                        break;
                    case 'o':
                        fileName = "o.gif";
                        break;
                    default:
                        fileName = null;
                }

                if (fileName != null)
                    imageView.setImage(new Image(getClass().getClassLoader().getResource("com/rockingstar/modules/TicTacToe/" + fileName).toURI().toString()));
            }
            else
                imageView.setImage(new Image(getClass().getClassLoader().getResource("com/rockingstar/modules/TicTacToe/empty.png").toURI().toString()));
        }

        catch (URISyntaxException | NullPointerException e) {
            Util.exit("Loading TicTacToe images");
        }

        Platform.runLater(() -> {
            if (_board[x][y] == null) {
                final int tempX = x;
                final int tempY = y;

                imageView.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (!_isFinished && _controller.getIsYourTurn()) {
                            imageView.setImage(null);
                            _controller.doPlayerMove(tempX, tempY);
                            imageView.removeEventFilter(MouseEvent.MOUSE_CLICKED, this);
                        }
                        else if (!_controller.getIsYourTurn())
                            _errorStatus.setText("It's not your turn, buddy");
                    }
                });
            }
        });

        Platform.runLater(() -> _pane.add(imageView, x, y));
    }

    public Button getNewGameButton() {
        return _newGameButton;
    }

    public Node getNode() {
        return _borderPane;
    }

    public void setBoard(Player[][] board) {
        _board = board;
    }

    public void setStatus(String status) {
        Platform.runLater(() -> _status.setText(status));
    }

    public void setErrorStatus(String errorStatus) {
        Platform.runLater(() -> _errorStatus.setText(errorStatus));
    }

    public void setIsFinished(boolean isFinished) {
        _isFinished = isFinished;
    }
}
