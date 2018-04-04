package Models;

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

//model
public class TTTModel extends Application {

    public char whoseTurn = 'X';

    public CellModel[][] cell = new CellModel[3][3];

    public Label status = new Label("X's turn to play");

    //model
    @Override
    public void start(Stage primaryStage) {

        Button end = new Button("Exit");
        Button newGame = new Button("Play Again?");
        end.setStyle("-fx-font-size: 15pt;");
        newGame.setStyle("-fx-font-size: 15pt;");

        end.setOnAction(e -> Platform.exit());

        GridPane pane = new GridPane();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                pane.add(cell[i][j] = new CellModel(), j, i);


        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pane);
        borderPane.setBottom(status);

        HBox buttons = new HBox();
        buttons.setSpacing(60.0);
        buttons.setMinHeight(50);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(newGame, end);

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
                    gridPane.add(cell[i][j] = new CellModel(), j, i);

            borderPane.setCenter(gridPane);
            // Change the turn
            whoseTurn = (whoseTurn == 'X') ? 'O' : 'X';
            // Display whose turn
            status.setText(whoseTurn + "'s turn.");
        });
    }
}
