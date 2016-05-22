package Flonarnia.Panels;

import Flonarnia.Heroes.Player;
import Flonarnia.Scenes.Location.Location;
import javafx.collections.ObservableMap;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Created by Alexander on 30.04.2016.
 */
public class SkillPanel extends Panel {
    private ArrayList<Cell> cells = new ArrayList<>();

    public SkillPanel(Pane pane, Player player, ArrayList<Location> locations) {
        super(pane);

        StackPane skillPane = new StackPane();

        int columnCount = 5;

        Cell cell0 = new Cell((0 % columnCount) * 50 + 5, 0);
        cell0.setBindAmount(false);
        cell0.addItem(new InventoryItem("attack", "item"));
        Cell cell1 = new Cell((1 % columnCount) * 50 + 5, 0);
        cell1.addItem(new InventoryItem("scroll", "item"));
        Cell cell2 = new Cell((2 % columnCount) * 50 + 5, 0);
        cell2.addItem(new InventoryItem("health", "poison"));
        Cell cell3 = new Cell((3 % columnCount) * 50 + 5, 0);
        cell3.addItem(new InventoryItem("endurance", "poison"));
        Cell cell4 = new Cell((4 % columnCount) * 50 + 5, 0);
        cell4.addItem(new InventoryItem("mana", "poison"));
        cells.add(cell0);
        cells.add(cell1);
        cells.add(cell2);
        cells.add(cell3);
        cells.add(cell4);

        cell1.setOnMouseClicked(event -> player.toVillage(locations, false));
        cell2.setOnMouseClicked(event -> player.usePoison("health", Color.RED));
        cell3.setOnMouseClicked(event -> player.usePoison("endurance", Color.GREEN));
        cell4.setOnMouseClicked(event -> player.usePoison("mana", Color.BLUE));

        Pane cellsPane = new Pane();
        cellsPane.getChildren().addAll(cells);

        skillPane.getChildren().addAll(cellsPane);
        skillPane.setStyle("-fx-background-color: black; -fx-opacity: 0.8;");
        skillPane.setPrefSize(50 * columnCount + 15, 50 + 3);
        this.getChildren().addAll(skillPane);
        pane.widthProperty().addListener((obs,old,newValue)->{
            this.setTranslateX(newValue.doubleValue() / 2 - this.getWidth() / 2);
        });
        pane.heightProperty().addListener((obs,old,newValue)->{
            this.setTranslateY(newValue.doubleValue() - this.getHeight() - 20);
        });
    }

    public void bindCells(ObservableMap<String, InventoryItem> items){
        cells.get(1).setLabelBinding(items.get("scroll").Amount.asString());
        cells.get(2).setLabelBinding(items.get("health").Amount.asString());
        cells.get(3).setLabelBinding(items.get("endurance").Amount.asString());
        cells.get(4).setLabelBinding(items.get("mana").Amount.asString());
    }

}
