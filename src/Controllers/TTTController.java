package Controllers;

import javafx.application.Application;
import javafx.geometry.Insets;
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
import Models.*;
import Controllers.*;

public class TTTController extends TTTModel {

    /** Determine if the cell are all occupied */
    public  boolean isFull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (cell[i][j].token == ' ')
                    return false;
        return true;
    }

    public boolean hasWon(char token) {
        for (int i = 0; i < 3; i++)
            if (cell[i][0].token == token
                    && cell[i][1].token == token
                    && cell[i][2].token == token) {
                return true;
            }

        for (int j = 0; j < 3; j++)
            if (cell[0][j].token ==  token
                    && cell[1][j].token == token
                    && cell[2][j].token == token) {
                return true;
            }

        if (cell[0][0].token == token
                && cell[1][1].token == token
                && cell[2][2].token == token) {
            return true;
        }

        if (cell[0][2].token == token
                && cell[1][1].token == token
                && cell[2][0].token == token) {
            return true;
        }

        return false;
    }
}
