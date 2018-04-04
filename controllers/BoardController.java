package com.rockingstar.modules.TicTacToe.controllers;

import com.rockingstar.modules.TicTacToe.models.Cell;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

public class BoardController {

    private Cell[][] _cells = new Cell[3][3];
    /*
    public char getToken() {
        return token;
    }

    public void setToken(char c) {
        token = c;

        if (token == 'X'){
            Image image = new Image(getClass().getResourceAsStream("src/x.gif"));
            Label xIcon = new Label("", new ImageView(image));
            xIcon.setPadding(new Insets(10,10,10,45));


            this.getChildren().addAll(xIcon);
        } else if (token == 'O') {
            Image image = new Image(getClass().getResourceAsStream("src/o.gif"));
            Label oIcon = new Label("", new ImageView(image));
            oIcon.setPadding(new Insets(10,10,10,45));

            getChildren().add(oIcon);
        }
    }

    public void handleMouseClick() {
        // If cell is empty and game is not over
        if (token == ' ' && TTTM.whoseTurn != ' ') {
            setToken(TTTM.whoseTurn); // Set token in the cell

            // Check game status
            if (TTTC.hasWon(TTTM.whoseTurn)) {
                TTTM.status.setText(TTTM.whoseTurn + " won! Do you want to play again?");
                TTTM.whoseTurn = ' '; // Game is over
            }
            else if (TTTC.isFull()) {
                TTTM.status.setText("Draw! Do you want to play again?");
                TTTM.whoseTurn = ' '; // Game is over
            }
            else {
                // Change the turn
                TTTM.whoseTurn = (TTTM.whoseTurn == 'X') ? 'O' : 'X';
                // Display whose turn
                TTTM.status.setText(TTTM.whoseTurn + "'s turn.");
            }
        }
    }*/

    public  boolean isFull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (_cells[i][j].getPlayer() == null)
                    return false;

        return true;
    }
}
