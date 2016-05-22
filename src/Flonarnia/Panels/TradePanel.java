package Flonarnia.Panels;

import Flonarnia.Scenes.Flonarnia;
import javafx.beans.binding.StringBinding;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

/**
 * Created by Alexander on 05.05.2016.
 */
public class TradePanel extends Panel {
    private double height = 300;
    private double width = 300;
    private ArrayList<Cell> cells = new ArrayList<>();

    private Label shekel = new Label("shekels: ");
    private Label cost = new Label("cost: ");
    private TextField enterAmount = new TextField();

    private Button hideButton = new Button("X");
    private Button buyButton = new Button("Buy");

    private Cell selectedCell;

    private Pane cellsPane = new Pane();

    public TradePanel(double translateX, double translateY, Pane pane){
        super(translateX, translateY, pane);
        pane.widthProperty().addListener((obs,old,newValue)->{
            this.setTranslateX(newValue.doubleValue() / 2 - this.getWidth() / 2);
        });
        pane.heightProperty().addListener((obs,old,newValue)->{
            this.setTranslateY(newValue.doubleValue() / 2 - this.getHeight() / 2);
        });

        label.setText("Store");
        label.setStyle("-fx-text-fill: goldenrod; -fx-font-style: italic; -fx-font-weight: bold;");

        shekel.textProperty().bind(new StringBinding() {
            {
                bind(Flonarnia.player.shekelAmount);
            }
            protected String computeValue() {
                return "shekels: : " + Flonarnia.player.shekelAmount.get();
            }
        });
        shekel.setStyle("-fx-text-fill: goldenrod; -fx-font-style: italic; -fx-font-weight: bold;");
        cost.setStyle("-fx-text-fill: goldenrod; -fx-font-style: italic; -fx-font-weight: bold;");

        hideButton.setStyle("-fx-background-color: black; -fx-opacity: 0.4;" +
                " -fx-font-size: 14px; -fx-text-fill: yellow;");
        hideButton.setOnAction(event -> this.setVisible(false));

        buyButton.setStyle("-fx-background-color: grey; -fx-text-fill: yellow;");
        buyButton.setPrefSize(50, 20);

        buyButton.setOnAction(event -> {
            InventoryItem item = selectedCell.getItem();
            if (enterAmount.getText().isEmpty() || item.kind.compareTo("weapon") == 0){
                int amount = 1;
                item.Amount.set(amount);
                Flonarnia.player.buyItem(item, item.cost * amount);
                return;
            }
            try{
                int amount = Integer.parseInt(enterAmount.getText().trim());
                item.Amount.set(amount);
                Flonarnia.player.buyItem(item, item.cost * amount);
            }
            catch (Exception e){

            }
        });

        enterAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()){
                enterAmount.setStyle("-fx-border-color: #33cc33; -fx-border-width: 2;");
                return;
            }
            if (newValue.length() > 3){
                enterAmount.setText(oldValue);
                return;
            }
            try {
                int value = Integer.parseInt(newValue);
                enterAmount.setStyle("-fx-border-color: #33cc33; -fx-border-width: 2;");
            }
            catch (Exception e){
                enterAmount.setStyle("-fx-border-color: #ff1a1a; -fx-border-width: 2;");
            }
        });
        enterAmount.setPromptText("amount");
        enterAmount.setPrefWidth(70);
        enterAmount.setPrefHeight(20);

        shekel.setTranslateY(height - 20);
        shekel.setTranslateX(10);

        cost.setTranslateY(height - 50);
        cost.setTranslateX(10);

        enterAmount.setTranslateY(height - 50);
        enterAmount.setTranslateX(120);

        buyButton.setTranslateX(200);
        buyButton.setTranslateY(height - 50);

        hideButton.setTranslateX(height - 30);
        hideButton.setPrefSize(5, 5);

        label.setTranslateX(140);
        int rowCount = 4;
        int columnCount = 5;
        for (int i = 0; i < rowCount * columnCount; i++){
            Cell cell = new Cell((i % columnCount) * 50 + 25, (i / columnCount) * 50 + 20, cells, this);
            cell.setBindAmount(false);
            cells.add(cell);
        }

        cellsPane.getChildren().addAll(cells);
        this.getChildren().addAll(cellsPane);
        this.getChildren().addAll(label, hideButton, buyButton, shekel, cost, enterAmount);
        this.setStyle("-fx-background-color: black; -fx-opacity: 0.9; -fx-background-radius: 10;");
        this.setPrefSize(width, height);
        this.setVisible(false);
    }

    public void putOnSale(ArrayList<InventoryItem> items){
        int i = 0;
        for (InventoryItem item: items){
            cells.get(i++).addItem(item);
        }
    }

    public void createPoisonTrade(){
        cells.forEach(Cell::removeItem);
        ArrayList<InventoryItem> items = new ArrayList<>();
        items.add(new InventoryItem("mana", "poison", 50));
        items.add(new InventoryItem("health", "poison", 300));
        items.add(new InventoryItem("endurance", "poison", 150));
        items.add(new InventoryItem("scroll", "item", 200));
        putOnSale(items);
    }

    public void createWeaponTrade(){
        cells.forEach(Cell::removeItem);
        ArrayList<InventoryItem> items = new ArrayList<>();
        items.add(new InventoryItem("Infinity Sharper", "weapon", 50000));
        items.add(new InventoryItem("Forgotten Sword", "weapon", 2000));
        items.add(new InventoryItem("Infinity Cutter", "weapon", 3000));
        items.add(new InventoryItem("Saint Spear", "weapon", 6000));
        items.add(new InventoryItem("Sword of Life", "weapon", 1000));
        items.add(new InventoryItem("Elemental Sword", "weapon", 2500));
        items.add(new InventoryItem("Dragon Slayer", "weapon", 3000));

        putOnSale(items);
    }

    public void setSelectedCell(Cell cell){
        this.selectedCell = cell;
        if (selectedCell.getItem() != null)
            cost.setText("cost: " + String.valueOf(selectedCell.getItem().cost));
    }
}
