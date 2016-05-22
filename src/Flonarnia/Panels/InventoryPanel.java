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
 * Created by Alexander on 30.04.2016.
 */
public class InventoryPanel extends Panel {
    private int itemCount = 1;
    private ArrayList<Cell> cells = new ArrayList<>();
    private Cell selectedCell;

    public InventoryPanel(double translateX, double translateY, Pane pane){
        super(translateX, translateY, pane);
        pane.widthProperty().addListener((obs,old,newValue)->{
            this.setTranslateX(newValue.doubleValue() / 2 - this.getWidth() / 2);
        });
        pane.heightProperty().addListener((obs,old,newValue)->{
            this.setTranslateY(newValue.doubleValue() / 2 - this.getHeight() / 2);
        });

        label.setText("Inventory");
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

        int rowCount = 4;
        int columnCount = 5;

        Cell c = new Cell((2 % columnCount) * 50 + 25, (columnCount - 1) * 50 + 20);
        c.setBindAmount(false);
        c.addItem(new InventoryItem("Sword of Miracles", "weapon", 1000));
        c.setOnMouseClicked(e->{
            if (c.getItem() != null){
                c.removeItem();
                Flonarnia.player.changeSword(null);
            }
        });

        for (int i = 0; i < rowCount * columnCount; i++){
            Cell cell = new Cell((i % columnCount) * 50 + 25, (i / columnCount) * 50 + 20);
            cell.setOnMouseClicked(e->{
                for (Cell c_: cells){
                    c_.setSelected(false);
                }
                cell.setSelected(true);
                if (cell.getItem() != null && cell.getItem().kind == "weapon"){
                    c.addItem(cell.getItem());
                    Flonarnia.player.changeSword(cell.getItem());
                    System.out.println("selected");
                }
                if (cell.getItem() != null){
                    System.out.println(cell.getItem().name + " " + cell.getItem().kind + " " + cell.getItem().Amount.get());
                }
            });
            cells.add(cell);
        }

        cells.add(c);
        Pane cellsPane = new Pane();
        cellsPane.getChildren().addAll(cells);

        inventoryPane.getChildren().addAll(cellsPane);
        inventoryPane.getChildren().addAll(label, hideButton, shekel);
        inventoryPane.setStyle("-fx-background-color: black; -fx-opacity: 0.9; -fx-background-radius: 10;");
        inventoryPane.setPrefSize(300, 300);

        this.setVisible(false);
        this.getChildren().addAll(inventoryPane);
    }
    public void updateCells(ObservableMap<String, InventoryItem> items){
        int counter = 0;
        for (InventoryItem item: items.values()){
            cells.get(counter).removeItem();
            cells.get(counter++).addItem(item);
        }
        itemCount = counter + 1;
    }
    public void addItem(InventoryItem item){
        cells.get(itemCount - 1).addItem(item);
        itemCount++;
    }

    public void setSelectedCell(Cell cell){
        this.selectedCell = cell;
    }
}
