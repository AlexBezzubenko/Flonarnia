package Panels;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

/**
 * Created by Alexander on 30.04.2016.
 */
public class Inventory extends Panel {
    public Inventory(double translateX, double translateY){
        super(translateX, translateY);
        label.setText("Inventory");
        label.setStyle("-fx-text-fill: goldenrod; -fx-font-style: italic; -fx-font-weight: bold; -fx-padding: 0 0 20 0;");
        StackPane inventoryPane = new StackPane();
        StackPane.setAlignment(label, Pos.TOP_CENTER);

        Button hideButton = new Button("X");
        StackPane.setAlignment(hideButton, Pos.TOP_RIGHT);
        hideButton.setStyle("-fx-background-color: black; -fx-opacity: 0.4; -fx-padding: -2 0 -2 0;" +
                " -fx-font-size: 14px; -fx-text-fill: yellow;");
        hideButton.setOnAction(event -> this.setVisible(false));

        ArrayList<Rectangle> cells = new ArrayList<>();
        int rowCount = 5;
        int columnCount = 5;
        for (int i = 0; i < rowCount * columnCount; i++){
            Rectangle rect = new Rectangle(50, 50, Color.DIMGREY);
            rect.setStrokeWidth(3);
            rect.setStroke(Color.BLACK);
            rect.setTranslateX((i % columnCount) * 50 + 25);
            rect.setTranslateY((i / rowCount) * 50 + 20);
            //rect.setFill(new ImagePattern());

            cells.add(rect);
        }

        Pane cellsPane = new Pane();
        cellsPane.getChildren().addAll(cells);

        inventoryPane.getChildren().addAll(cellsPane);
        inventoryPane.getChildren().addAll(label, hideButton);
        inventoryPane.setStyle("-fx-background-color: black; -fx-opacity: 0.8; -fx-background-radius: 10;");
        inventoryPane.setPrefSize(300, 300);
        this.getChildren().addAll(inventoryPane);

    }
}
