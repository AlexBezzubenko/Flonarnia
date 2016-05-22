package Flonarnia.Panels;

import Flonarnia.tools.ImageManager;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

/**
 * Created by Alexander on 30.04.2016.
 */
public class Cell extends StackPane{
    private Rectangle rect;
    private ImageView imageView = new ImageView();
    private Label label = new Label();
    private final int cellSize = 50;
    private boolean bindAmount = true;
    private InventoryItem inventoryItem;

    public Cell(double translateX, double translateY){
        label.setStyle("-fx-text-fill: goldenrod; -fx-font-style: italic; -fx-font-weight: bold; " +
                "-fx-padding: 30 27 0 0;");
        rect = new Rectangle(cellSize, cellSize, Color.DIMGREY);
        rect.setStrokeWidth(2);
        rect.setStroke(Color.BLACK);
        imageView.setFitWidth(cellSize);
        imageView.setFitHeight(cellSize);


        this.setTranslateX(translateX);
        this.setTranslateY(translateY);
        this.getChildren().addAll(rect, imageView, label);
    }
    public Cell(double translateX, double translateY, ArrayList<Cell> cells, TradePanel panel){
        this(translateX, translateY);

        this.setOnMouseClicked(e->{
            for (Cell cell: cells){
                cell.setSelected(false);
            }
            panel.setSelectedCell(this);
            setSelected(true);
        });
    }

    public void setBindAmount(boolean bind){
        this.bindAmount = bind;
    }

    public void setLabelBinding(ObservableValue<? extends String> observable){
        this.label.textProperty().bind(observable);
    }

    public void setSelected(boolean isSelected){
        if (isSelected)
            this.rect.setStroke(Color.YELLOW);
        else
            this.rect.setStroke(Color.BLACK);
        this.toFront();
    }

    public InventoryItem getItem(){
        return inventoryItem;
    }

    public void addItem(InventoryItem item){
        this.inventoryItem = item;
        if (bindAmount && item.kind != "weapon") {
            label.textProperty().bind(item.Amount.asString());
        }
        else{
            label.textProperty().unbind();
            label.setText("");
        }

        imageView.setImage(ImageManager.getInstance().getIcon(item.kind));
        double[] params = ImageManager.getInstance().getParams("Icons", item.name);
        if (params != null)
            imageView.setViewport(new Rectangle2D(params[0], params[1], params[2], params[3]));
        imageView.setFitWidth(48);
        imageView.setFitHeight(48);

        Tooltip t = new Tooltip();
        if (inventoryItem.kind == "weapon"){
            t.setText(inventoryItem.name + "\npower = " + inventoryItem.cost / 10);
        }
        else{
            t.setText(inventoryItem.name);
        }
        Tooltip.install(this, t);

    }
    public void removeItem(){
        imageView.setImage(null);
    }
}
