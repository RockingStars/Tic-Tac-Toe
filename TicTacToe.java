package com.rockingstar.modules.TicTacToe;

import com.rockingstar.engine.game.AbstractGame;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.application.Platform;
import javafx.scene.image.Image;

public class TicTacToe extends AbstractGame {

    private char whoseTurn = 'X';

    private Cell[][] cell = new Cell[3][3];

    private Label status = new Label("X's turn to play");

    @Override
    public Node getView() {
        return null;
    }

    public void start(Stage primaryStage) {

        Button end = new Button("Exit");
        Button newGame = new Button("Play Again?");
        end.setStyle("-fx-font-size: 15pt;");
        newGame.setStyle("-fx-font-size: 15pt;");

        end.setOnAction(e -> Platform.exit());

        GridPane pane = new GridPane();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                pane.add(cell[i][j] = new Cell(), j, i);


        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pane);
        borderPane.setBottom(status);

        HBox buttons = new HBox();
        buttons.setSpacing(60.0);
        buttons.setMinHeight(50);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(newGame,end);

        borderPane.setTop(buttons);

        Scene scene = new Scene(borderPane, 550, 470);
        primaryStage.setTitle("Rocking Star Games");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);

        newGame.setOnAction(e -> {
            GridPane gridPane = new GridPane();
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    gridPane.add(cell[i][j] = new Cell(), j, i);

            borderPane.setCenter(gridPane);
            // Change the turn
            whoseTurn = (whoseTurn == 'X') ? 'O' : 'X';
            // Display whose turn
            status.setText(whoseTurn + "'s turn.");
        });
    }

    /** Determine if the cell are all occupied */
    public boolean isFull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (cell[i][j].getToken() == ' ')
                    return false;
        return true;
    }

    /** Determine if the player with the specified token wins */
    public boolean hasWon(char token) {
        for (int i = 0; i < 3; i++)
            if (cell[i][0].getToken() == token
                    && cell[i][1].getToken() == token
                    && cell[i][2].getToken() == token) {
                return true;
            }

        for (int j = 0; j < 3; j++)
            if (cell[0][j].getToken() ==  token
                    && cell[1][j].getToken() == token
                    && cell[2][j].getToken() == token) {
                return true;
            }

        if (cell[0][0].getToken() == token
                && cell[1][1].getToken() == token
                && cell[2][2].getToken() == token) {
            return true;
        }

        if (cell[0][2].getToken() == token
                && cell[1][1].getToken() == token
                && cell[2][0].getToken() == token) {
            return true;
        }

        return false;
    }


    public class Cell extends Pane {

        private char token = ' ';

        public Cell() {
            setStyle("-fx-border-color: black");
            this.setPrefSize(200,200);
            this.setOnMouseClicked(e -> handleMouseClick());
        }

        /** Return token */
        public char getToken() {
            return token;
        }

        /** Set a new token */
        public void setToken(char c) {
            token = c;

            if (token == 'X'){
               Image image = new Image(getClass().getResourceAsStream("x.gif"));
               Label xIcon = new Label("", new ImageView(image));
               xIcon.setPrefSize(150,150);


               this.getChildren().addAll(xIcon);
            } else if (token == 'O') {
                Image image = new Image(getClass().getResourceAsStream("o.gif"));
                Label oIcon = new Label("", new ImageView(image));

                getChildren().add(oIcon);
            }
        }

        /* Handle a mouse click event */
        private void handleMouseClick() {
            // If cell is empty and game is not over
            if (token == ' ' && whoseTurn != ' ') {
                setToken(whoseTurn); // Set token in the cell

                // Check game status
                if (hasWon(whoseTurn)) {
                    status.setText(whoseTurn + " won! Do you want to play again?");
                    whoseTurn = ' '; // AbstractGame is over
                }
                else if (isFull()) {
                    status.setText("Draw! Do you want to play again?");
                    whoseTurn = ' '; // AbstractGame is over
                }
                else {
                    // Change the turn
                    whoseTurn = (whoseTurn == 'X') ? 'O' : 'X';
                    // Display whose turn
                    status.setText(whoseTurn + "'s turn.");
                }
            }
        }
    }
}
