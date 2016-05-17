package Flonarnia.Heroes;

import Flonarnia.Flobjects.Flobject;
import Flonarnia.Flobjects.Portal;
import Flonarnia.Panels.InventoryItem;
import Flonarnia.Panels.InventoryPanel;
import Flonarnia.Panels.SkillPanel;
import Flonarnia.Scenes.Flonarnia;
import Flonarnia.tools.Collision;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.CacheHint;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.util.Duration;

import java.io.*;

/**
 * Created by Alexander on 22.03.2016.
 */

public class Player  extends Character {
    public static SimpleIntegerProperty shekelAmount = new SimpleIntegerProperty(1000);
    public SimpleIntegerProperty level = new SimpleIntegerProperty(1);

    public SimpleIntegerProperty healthCapacity = new SimpleIntegerProperty(800);
    public SimpleIntegerProperty healthMaxCapacity = new SimpleIntegerProperty(1000);

    public SimpleIntegerProperty manaCapacity = new SimpleIntegerProperty(800);
    public SimpleIntegerProperty manaMaxCapacity = new SimpleIntegerProperty(1000);

    public SimpleIntegerProperty enduranceCapacity = new SimpleIntegerProperty(800);
    public SimpleIntegerProperty enduranceMaxCapacity = new SimpleIntegerProperty(1000);

    private InventoryItem sword;
    public  InventoryPanel inventoryPanel;
    private  ObservableMap<String, InventoryItem> inventory = FXCollections.observableHashMap();

    private final int attackRadius = 100;
    private int power = 100;

    private Flobject target;

    private FadeTransition poisonTransition;
    private Ellipse poisonUsingEllipse;
    private boolean canMove = true;

    public Player(double translateX, double translateY){
        super(translateX, translateY, "player");
        sword = new InventoryItem("Sword of Miracles", "weapon", 1000);
        inventoryPanel = new InventoryPanel(300, 300, Flonarnia.appRoot);

        inventory.put("mana", new InventoryItem("mana", "poison"));
        inventory.put("health", new InventoryItem("health", "poison"));
        inventory.put("endurance", new InventoryItem("endurance", "poison"));
        inventory.put("scroll", new InventoryItem("scroll", "item"));
        inventory.put("Sword of Miracles", sword);

        inventoryPanel.updateCells(inventory);

        /*inventory.addListener((MapChangeListener) change -> {
            System.out.println("Detected a change! ");
            inventoryPanel.updateCells(inventory);
        });*/
        poisonUsingEllipse = new Ellipse();
        {
            poisonUsingEllipse.setCenterX(15.0f);
            poisonUsingEllipse.setCenterY(50.0f);
            poisonUsingEllipse.setRadiusX(15.0f);
            poisonUsingEllipse.setRadiusY(5.0f);
        }

        //ellipse.getTransforms().add(new Rotate(3, 3, 30, 30));
        poisonUsingEllipse.setFill(Color.TRANSPARENT);
        poisonUsingEllipse.setStroke(Color.RED);
        poisonUsingEllipse.setStrokeWidth(3);
        poisonUsingEllipse.setOpacity(0);

        poisonTransition = new FadeTransition(Duration.millis(300), poisonUsingEllipse);
        poisonTransition.setFromValue(0);
        poisonTransition.setToValue(1.0);
        poisonTransition.setCycleCount(2);
        poisonTransition.setAutoReverse(true);


        poisonUsingEllipse.setTranslateY(-HEIGHT);
        this.getChildren().add(poisonUsingEllipse);
        this.getChildren().removeAll(visual);
    }

    public Flobject getTarget(){
        return target;
    }
    public void usePoison(String poison, Color color){
        InventoryItem existItem = inventory.get(poison);
        if (existItem != null){
            if (existItem.Amount.get() > 0) {
                System.out.println(existItem.Amount.get() + "  before");
                inventory.remove(poison);
                existItem.removeItem(1);
                System.out.println(existItem.Amount.get() + "  after");
                inventory.put(poison, existItem);
                switch (poison){
                    case "health":
                        regenerateHP(100);
                        break;
                    case "mana":
                        regenerateMP(100);
                        break;
                    case "endurance":
                        regenerateED(100);
                        break;
                }
                poisonUsingEllipse.setStroke(color);
                poisonTransition.play();
            }
            else {
                Flonarnia.logPanel.addLine("You have no more " + poison);
            }
        }
    }
    public void toVillage(Portal portal){
        InventoryItem existItem = inventory.get("scroll");
        if (existItem != null){
            if (existItem.Amount.get() > 0) {
                inventory.remove("scroll");
                existItem.removeItem(1);
                inventory.put("scroll", existItem);
                toLocation(portal, "Dion");
            }
        }
    }
    public void toLocation(Portal portal, String location){
        if (portal != null){
            moveY(0.0001);
            double x = portal.getTranslateX() + portal.getBounds().getWidth() / 2 - WIDTH / 2;
            double y = portal.getTranslateY() + portal.getBounds().getHeight() / 2 - HEIGHT;
            this.setTranslateX((int)x);
            this.setTranslateY((int)y);
            portal.openPortal();
            this.setVisible(false);
            canMove = false;
            new Timeline(new KeyFrame(
                    Duration.millis(2500),
                    ae -> {this.setVisible(true); canMove = true;}), new KeyFrame(Duration.millis(4500),
                    ae -> portal.closePortal()))
                    .play();
            Flonarnia.logPanel.addLine(String.format("Welcome to %s!", location));
        }
    }
    public void changeSword(InventoryItem sword){
        //hero power level 0 = sword.cost / 100;
        if (sword != null && sword.kind == "weapon"){
            this.sword = sword;
            this.power = sword.cost / 10;
            System.out.println("power = " + power);
        }
        if (sword == null){
            this.sword = sword;
        }
    }

    public void buyItem(InventoryItem item, int cost){
        if (shekelAmount.get() - cost >= 0) {
            addToInventory(item);
            shekelAmount.set(shekelAmount.get() - cost);
        }
    }

    public void addToInventory(InventoryItem item){
        InventoryItem existItem = inventory.get(item.name);
        if (existItem != null){
            System.out.println("existed");
            inventory.remove(item.name);
            existItem.addItem(item.Amount.get());
            inventory.put(item.name, existItem);
        }
        else{
            System.out.println("null. Create");
            inventoryPanel.addItem(item);
            inventory.put(item.name, item);
        }
    }

    public void Bind(SkillPanel skillPanel){
        skillPanel.bindCells(inventory);
    }


    public void moveX(double value){
        if (!canMove)
            return;
        changeSize(WIDTH, HEIGHT);
        if (run){
            if (enduranceCapacity.get() <= 0)
                run = false;
            else {
                enduranceCapacity.set(enduranceCapacity.get() - 10);
                value *= 2;
        }
        }
        else {
            /*int increment = 5;
            if (enduranceCapacity.get() + increment > enduranceMaxCapacity.get())
                increment = 0;
            enduranceCapacity.set(enduranceCapacity.get() + increment);*/
        }
        super.moveX(value);
    }
    public void moveY(double value){
        if (!canMove)
            return;
        changeSize(WIDTH, HEIGHT);
        if (run) {
            if (enduranceCapacity.get() <= 0)
                run = false;
            else {
                enduranceCapacity.set(enduranceCapacity.get() - 10);
                value *= 2;
            }
        }
        super.moveY(value);
    }

    public void attack(){
        if (sword == null)
            return;
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
                    Enemy enemy = ((Enemy)target);
                    enemy.health.set(((Enemy)target).health.get() - power);
                    if (enemy.health.get() <= 0) {
                        Flonarnia.foregroundRoot.getChildren().remove(target);
                        Flonarnia.flobjects.remove(target);
                        target = null;
                        shekelAmount.set(shekelAmount.getValue() + 1000);
                        if (level.get() < 80)
                            level.set(level.get() + 2);
                    }
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
        healthCapacity.set(healthCapacity.get() - 50);
        //healthMaxCapacity.set(healthMaxCapacity.get() - 50);

        manaCapacity.set(manaCapacity.get() - 50);
        //manaMaxCapacity.set(manaMaxCapacity.get() - 50);

        enduranceCapacity.set(enduranceCapacity.get() - 50);
        //enduranceMaxCapacity.set(enduranceMaxCapacity.get() - 50);
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), this);
        double sign = value / Math.abs(value);
        double throwLen = 20;
        if (X) {
            this.setTranslateX(this.getTranslateX() + sign * throwLen);
            translateTransition.setFromX(this.getTranslateX());
            translateTransition.setToX(this.getTranslateX() + value);
            translateTransition.setCycleCount(1);
            translateTransition.play();

            if (Collision.checkTranslateX(this, Flonarnia.flobjects, 0) != null) {
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

            if (Collision.checkTranslateY(this, Flonarnia.flobjects, 0) != null) {
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
        if (!isRunning) {
            run = false;
            if (target != this) {
                double deltaX = this.getTranslateX() - target.getTranslateX();
                this.moveX(-0.0001 * (deltaX / Math.abs(deltaX)));
            }
        }
    }

    public void regenerate(){
        int manaValue = 3;
        int healthValue = 3;
        int enduranceValue = 7;
        regenerateHP(healthValue);
        regenerateMP(manaValue);
        regenerateED(enduranceValue);
    }
    private void regenerateHP(int healthValue){
        if (healthCapacity.get() + healthValue > healthMaxCapacity.get())
            healthValue = healthMaxCapacity.get() - healthCapacity.get();
        healthCapacity.set(healthCapacity.get() + healthValue);
    }
    private void regenerateMP(int manaValue){
        if (manaCapacity.get() + manaValue > manaMaxCapacity.get())
            manaValue = manaMaxCapacity.get() - manaCapacity.get();
        manaCapacity.set(manaCapacity.get() + manaValue);
    }
    private void regenerateED(int enduranceValue){
        if (enduranceCapacity.get() + enduranceValue > enduranceMaxCapacity.get())
            enduranceValue = enduranceMaxCapacity.get() - enduranceCapacity.get();
        enduranceCapacity.set(enduranceCapacity.get() + enduranceValue);
    }
    public void spend(){
        int manaValue = 200;
        int healthValue = 200;
        int enduranceValue = 200;
        spendHP(healthValue);
        spendMP(manaValue);
        spendED(enduranceValue);
    }
    private void spendHP(int healthValue){
        if (healthCapacity.get() - healthValue < 0)
            healthValue = healthCapacity.get();
        healthCapacity.set(healthCapacity.get() - healthValue);
    }
    private void spendMP(int manaValue){
        if (manaCapacity.get() - manaValue < 0)
            manaValue = manaCapacity.get();
        manaCapacity.set(manaCapacity.get() - manaValue);
    }
    private void spendED(int enduranceValue){
        if (enduranceCapacity.get() - enduranceValue < 0)
            enduranceValue = enduranceCapacity.get();
        enduranceCapacity.set(enduranceCapacity.get() - enduranceValue);
    }


    public void saveState(){
        try(DataOutputStream file = new DataOutputStream(new FileOutputStream("hero.bin")))
        {
            file.writeDouble(this.getTranslateX());
            file.writeDouble(this.getTranslateY());
            file.writeInt(level.get());
            file.writeInt(shekelAmount.get());
            file.writeInt(healthCapacity.get());
            file.writeInt(healthMaxCapacity.get());

            file.writeInt(manaCapacity.get());
            file.writeInt(manaMaxCapacity.get());

            file.writeInt(enduranceCapacity.get());
            file.writeInt(enduranceMaxCapacity.get());

            file.writeInt(inventory.size());
            for(InventoryItem i: inventory.values())
            {
                file.writeUTF(i.name);
                file.writeUTF(i.kind);
                file.writeInt(i.cost);
                file.writeInt(i.Amount.get());
            }
            Flonarnia.logPanel.addLine("State was saved");
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }
    public void loadState(){
        try(DataInputStream file = new DataInputStream(new FileInputStream("hero.bin")))
        {
            this.setTranslateX((int)file.readDouble());
            this.setTranslateY((int)file.readDouble());
            level.set(file.readInt());
            shekelAmount.set(file.readInt());
            healthCapacity.set(file.readInt());
            healthMaxCapacity.set(file.readInt());
            manaCapacity.set(file.readInt());
            manaMaxCapacity.set(file.readInt());
            enduranceCapacity.set(file.readInt());
            enduranceMaxCapacity.set(file.readInt());

            inventory.clear();
            inventoryPanel.setVisible(false);

            int size = file.readInt();
            for (int i = 0; i < size; i++){
                String name = file.readUTF();
                String kind = file.readUTF();

                if ((kind.compareTo("weapon") == 0)){
                    kind = "weapon";
                }
                int cost = file.readInt();
                int amount = file.readInt();
                InventoryItem item = new InventoryItem(name, kind, cost);
                item.addItem(amount);
                inventory.put(name, item);
            }

            inventoryPanel.updateCells(inventory);

            sword = inventory.get("Sword of Miracles");
            power = sword.cost / 10;

            Flonarnia.logPanel.addLine("State was loaded");
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        this.Bind(Flonarnia.skillPanel);
    }
}
