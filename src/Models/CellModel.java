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
import Controllers.CellController;

public class CellModel extends Pane {
        public char token = ' ';
        public CellController CC = new CellController();

        public CellModel() {
            setStyle("-fx-border-color: black");
            this.setPrefSize(200,200);
            this.setOnMouseClicked(e -> CC.handleMouseClick());
        }
}
