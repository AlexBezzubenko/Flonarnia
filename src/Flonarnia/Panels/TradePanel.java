package Flonarnia.Panels;

import Flonarnia.Scenes.Flonarnia;
import javafx.beans.binding.StringBinding;
import javafx.collections.ObservableMap;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

/**
 * Created by Alexander on 05.05.2016.
 */
public class TradePanel extends Panel {
    private ArrayList<Cell> cells = new ArrayList<>();
    public TradePanel(double translateX, double translateY, Pane pane){
        super(translateX, translateY, pane);
        label.setText("Store");
        label.setStyle("-fx-text-fill: goldenrod; -fx-font-style: italic; -fx-font-weight: bold; -fx-padding: 0 0 20 0;");
        StackPane inventoryPane = new StackPane();
        StackPane.setAlignment(label, Pos.TOP_CENTER);

        Label shekel = new Label("shekels: ");
        shekel.textProperty().bind(new StringBinding() {
            {
                bind(Flonarnia.player.shekelAmount);
            }
            protected String computeValue() {
                return "shekels: : " + Flonarnia.player.shekelAmount.get();
            }
        });
        shekel.setStyle("-fx-text-fill: goldenrod; -fx-font-style: italic; -fx-font-weight: bold; -fx-padding: 0 0 5 30;");
        StackPane.setAlignment(shekel, Pos.BOTTOM_LEFT);

        Button hideButton = new Button("X");
        StackPane.setAlignment(hideButton, Pos.TOP_RIGHT);
        hideButton.setStyle("-fx-background-color: black; -fx-opacity: 0.4;" +
                " -fx-font-size: 14px; -fx-text-fill: yellow;");
        hideButton.setOnAction(event -> this.setVisible(false));

        /*int rowCount = 5;
        int columnCount = 5;
        for (int i = 0; i < rowCount * columnCount; i++){
            Cell cell = new Cell((i % columnCount) * 50 + 25, (i / rowCount) * 50 + 20);
            cells.add(cell);
        }*/

        Cell cell1 = new Cell(0 * 50 + 25, 0 * 50 + 20);
        Cell cell2 = new Cell(1 * 50 + 25, 1 * 50 + 20);

        cells.add(cell1);
        cells.add(cell2);

        Pane cellsPane = new Pane();
        cellsPane.getChildren().addAll(cells);

        inventoryPane.getChildren().addAll(cellsPane);
        inventoryPane.getChildren().addAll(label, hideButton, shekel);
        inventoryPane.setStyle("-fx-background-color: black; -fx-opacity: 0.9; -fx-background-radius: 10;");
        inventoryPane.setPrefSize(300, 300);
        this.getChildren().addAll(inventoryPane);
    }
    public void updateCells(ObservableMap<String, InventoryItem> items){
        cells.forEach(Cell::removeItem);

        int counter = 0;
        for (InventoryItem item: items.values()){
            cells.get(counter++).addItem(item);
        }
    }
}
