package Flonarnia.Heroes;

import Flonarnia.Flobjects.Flobject;
import Flonarnia.Scenes.Flonarnia;
import Flonarnia.Panels.InventoryItem;
import Flonarnia.Panels.InventoryPanel;
import Flonarnia.tools.Collision;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.scene.CacheHint;
import javafx.scene.effect.ColorAdjust;
import javafx.util.Duration;

import java.lang.*;

/**
 * Created by Alexander on 22.03.2016.
 */

public class Player  extends Character {
    public static SimpleIntegerProperty shekelAmount = new SimpleIntegerProperty(1000);
    public InventoryPanel inventoryPanel = new InventoryPanel(300, 300, Flonarnia.appRoot);
    private ObservableMap<String, InventoryItem> inventory = FXCollections.observableHashMap();

    private final int attackRadius = 100;
    private Flobject target;
    public Player(double translateX, double translateY){
        super(translateX, translateY, "player");
        inventory.put("mana", new InventoryItem("mana", "poison"));
        inventory.put("health", new InventoryItem("health", "poison"));
        inventory.put("endurance", new InventoryItem("endurance", "poison"));
        inventory.put("scroll", new InventoryItem("scroll", "item"));
        inventory.put("sword", new InventoryItem("sword", "item"));
        inventoryPanel.updateCells(inventory);

        inventory.addListener((MapChangeListener) change -> {
            System.out.println("Detected a change! ");
            inventoryPanel.updateCells(inventory);
        });
    }

    public void addToInventory(InventoryItem item){
        InventoryItem existItem = inventory.get(item.name);
        if (existItem != null){
            System.out.println("existed");
            inventory.remove(item.name);
            existItem.addItem(item.amount);
            inventory.put(item.name, existItem);

        }
        else{
            System.out.println("null. Create");
            inventory.put(item.name, item);
        }

    }

    @Override
    public void moveX(int value, boolean run){
        changeSize(WIDTH, HEIGHT);
        super.moveX(value, run);
    }
    @Override
    public void moveY(int value, boolean run){
        changeSize(WIDTH, HEIGHT);
        super.moveY(value, run);

    }


    public void attack(){
        changeSize(60, 70);
        if (target != null && target.getTranslateX() - this.getTranslateX() < 0){
            imageView.setScaleX(-1); ///??????? this displays image to right on 1 width
        }

        animation.setColumns(5);
        animation.setOffsetX(2);
        animation.setOffsetY(513);

        animation.play();
        animation.setOnFinished(event -> {
            if (target != null && target != this && target.getClass() == Enemy.class){
                double distance = target.getTranslateX() - this.getTranslateX();
                if (distance < 0){
                    imageView.setScaleX(-1); ///??????? this displays image to right on 1 width
                }
                if (Math.abs(distance) <= attackRadius){
                    Flonarnia.foregroundRoot.getChildren().remove(target);
                    Flonarnia.flobjects.remove(target);
                    target = null;
                }
            }

            changeSize(WIDTH, HEIGHT);
            imageView.setScaleX(1);
            animation.interpolate(0);
        });
    }
    private void changeSize(double width, double height){
        imageView.setScaleX(1);
        this.width = width;
        this.height = height;
        this.setPrefSize(width,height);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        animation.setWidthHeight(width,height);
    }
    public void attacked(double value, boolean X){
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), this);
        double sign = value / Math.abs(value);
        double throwLen = 20;
        if (X) {
            this.setTranslateX(this.getTranslateX() + sign * throwLen);
            translateTransition.setFromX(this.getTranslateX());
            translateTransition.setToX(this.getTranslateX() + value);
            translateTransition.setCycleCount(1);
            translateTransition.play();

            if (Collision.checkTranslteX(this, Flonarnia.flobjects, 0) != null) {
                translateTransition.stop();
                return;
            }
        }else{
            if (sign > 0){
                throwLen *= 3;
            }
            this.setTranslateY(this.getTranslateY() + sign * throwLen);
            translateTransition.setFromY(this.getTranslateY());
            translateTransition.setToY(this.getTranslateY() + value);
            translateTransition.setCycleCount(1);
            translateTransition.play();

            if (Collision.checkTranslteY(this, Flonarnia.flobjects, 0) != null) {
                translateTransition.stop();
                return;
            }
        }

        ColorAdjust blackout = new ColorAdjust();

        blackout.setContrast(0.2);
        blackout.setHue(-0.25);
        blackout.setBrightness(0);
        blackout.setSaturation(0.5);

        imageView.setEffect(blackout);
        imageView.setCache(true);
        imageView.setCacheHint(CacheHint.SPEED);

        translateTransition.setOnFinished(event -> {imageView.setEffect(null);});
    }
    public void changeTarget(Flobject target){
        this.target = target;
    }
}
