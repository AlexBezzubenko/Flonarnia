package Flonarnia.Panels;

import Flonarnia.tools.ImageManager;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by Alexander on 30.04.2016.
 */
public class Cell extends StackPane{
    private boolean empty = true;
    private Rectangle rect;
    private ImageView imageView = new ImageView();
    private Label label = new Label();
    private final int cellSize = 50;

    private SimpleIntegerProperty itemAmount = new SimpleIntegerProperty(0);

    public Cell(double translateX, double translateY){
        label.setStyle("-fx-text-fill: goldenrod; -fx-font-style: italic; -fx-font-weight: bold; " +
                "-fx-padding: 30 27 0 0;");
        rect = new Rectangle(cellSize, cellSize, Color.DIMGREY);
        rect.setStrokeWidth(3);
        rect.setStroke(Color.BLACK);
        imageView.setFitWidth(cellSize);
        imageView.setFitHeight(cellSize);
        this.setTranslateX(translateX);
        this.setTranslateY(translateY);
        this.getChildren().addAll(rect, imageView, label);
    }
    public void addItem(InventoryItem item){
        if (item.kind != "skills") {
            //label.setText(Integer.toString(item.amount));
            label.textProperty().bind(itemAmount.asString());
        }
        imageView.setImage(ImageManager.getInstance().getIcon(item.kind));
        double[] params = ImageManager.getInstance().getParams("Icons", item.name);
        if (params != null)
            imageView.setViewport(new Rectangle2D(params[0], params[1], params[2], params[3]));
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
    }
    public void removeItem(){
        label.textProperty().unbind();
        imageView.setImage(null);
    }
    public boolean isEmpty(){
        return empty;
    }
    public void incItemAmount(int value){
        int currentValue = itemAmount.getValue();
        if (currentValue + value < 1000){
            itemAmount.setValue(currentValue + value);
        }
    }
}
