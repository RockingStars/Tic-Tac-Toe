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

/**
 * This class handles the graphical part of the TicTacToe game.
 * @author Rocking Stars
 * @since 1.0 Beta 1
 */
public class TTTView {

    /**
     * Contains the status bars and the game board
     */
    private BorderPane _borderPane;

    /**
     * A button that ends the game
     */
    private Button _endButton;

    /**
     * A button that lets you start a new game
     */
    private Button _newGameButton;

    /**
     * Contains the player images
     */
    private GridPane _pane;

    /**
     * The game board
     */
    private Player[][] _board;

    /**
     * The current status
     */
    private Label _status;

    /**
     * Used for displaying errors, such as 'Not your turn'
     */
    private Label _errorStatus;

    /**
     * Points to the TTTController
     */
    private TTTController _controller;

    /**
     * Whether or not the game is finished
     */
    private boolean _isFinished;

    /**
     * TTTView constructor
     * @param controller The TicTacToe controller
     */
    public TTTView(TTTController controller) {
        _borderPane = new BorderPane();
        _controller = controller;

        _status = new Label();
        _status.setFont(new Font(16));
        _status.setId("topText");

        _errorStatus = new Label();
        _errorStatus.setFont(new Font(16));
        _errorStatus.setTextFill(Color.RED);

        _isFinished = false;

        setup();
    }

    /**
     * Sets up the view
     */
    private void setup() {
        _endButton = new Button("End game");
        _newGameButton = new Button("New game");

        _pane = new GridPane();
        _pane.setAlignment(Pos.CENTER);
        _pane.setPadding(new Insets(20));

        HBox buttons = new HBox();
        buttons.setSpacing(60.0);
        buttons.setMinHeight(50);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(_newGameButton, _endButton);

        HBox labels = new HBox();
        labels.setSpacing(60.0);
        labels.setMinHeight(50);
        labels.setAlignment(Pos.CENTER);
        labels.getChildren().addAll(_status, _errorStatus);

        _borderPane.setCenter(_pane);
        _borderPane.setBottom(buttons);
        _borderPane.setTop(labels);
    }

    /**
     * Displays an appropriate image for each cell
     */
    public void generateBoardVisual() {
        _pane.getChildren().clear();

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                setCellImage(i, j);
    }

    /**
     * Returns a pointer to _endButton
     * @return _endButton
     */
    public Button getEndButton() {
        return _endButton;
    }

    /**
     * Updates the image of a specific cell
     * @param x The x position
     * @param y The y position
     */
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

            _pane.add(imageView, x, y);
        });
    }

    /**
     * Returns the new game button
     * @return _newGameButton
     */
    public Button getNewGameButton() {
        return _newGameButton;
    }

    /**
     * Returns the view
     * @return A BordePane containing status bars and the game board
     */
    public Node getNode() {
        return _borderPane;
    }

    /**
     * Sets the game board
     * @param board The game board
     */
    public void setBoard(Player[][] board) {
        _board = board;
    }

    /**
     * Updates the status label
     * @param status The new status
     */
    public void setStatus(String status) {
        Platform.runLater(() -> _status.setText(status));
    }

    /**
     * Updates the error status
     * @param errorStatus The new error status
     */
    public void setErrorStatus(String errorStatus) {
        Platform.runLater(() -> _errorStatus.setText(errorStatus));
    }

    /**
     * Sets the value of _isFinished
     * @param isFinished The new value of _isFinished
     */
    public void setIsFinished(boolean isFinished) {
        _isFinished = isFinished;
    }
}
