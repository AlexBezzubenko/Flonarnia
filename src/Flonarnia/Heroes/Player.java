package Flonarnia.Heroes;

import Flonarnia.Flobjects.Flobject;
import Flonarnia.Flobjects.Portal;
import Flonarnia.Panels.InventoryItem;
import Flonarnia.Panels.InventoryPanel;
import Flonarnia.Panels.SkillPanel;
import Flonarnia.Scenes.Flonarnia;
import Flonarnia.Scenes.Location.Location;
import Flonarnia.tools.AttackedTransition;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.CacheHint;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Alexander on 22.03.2016.
 */

public class Player  extends Character {
    public static SimpleIntegerProperty shekelAmount = new SimpleIntegerProperty(1000);
    public SimpleIntegerProperty level = new SimpleIntegerProperty(1);

    public SimpleIntegerProperty healthCapacity = new SimpleIntegerProperty(100);
    public SimpleIntegerProperty healthMaxCapacity = new SimpleIntegerProperty(100);

    public SimpleIntegerProperty manaCapacity = new SimpleIntegerProperty(100);
    public SimpleIntegerProperty manaMaxCapacity = new SimpleIntegerProperty(100);

    public SimpleIntegerProperty enduranceCapacity = new SimpleIntegerProperty(100);
    public SimpleIntegerProperty enduranceMaxCapacity = new SimpleIntegerProperty(100);

    private InventoryItem sword;
    public  InventoryPanel inventoryPanel;
    private  ObservableMap<String, InventoryItem> inventory = FXCollections.observableHashMap();

    private final int attackRadius = 100;
    private int power = 100;
    private int exp = 0;
    private int maxExp = 100;
    private boolean alive = true;

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

        poisonUsingEllipse = new Ellipse();
        {
            poisonUsingEllipse.setCenterX(15.0f);
            poisonUsingEllipse.setCenterY(50.0f);
            poisonUsingEllipse.setRadiusX(15.0f);
            poisonUsingEllipse.setRadiusY(5.0f);
        }

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
    public void toVillage(ArrayList<Location> locations, boolean free){
        InventoryItem existItem = inventory.get("scroll");
        if ((existItem != null && existItem.Amount.get() > 0) || free){
            inventory.remove("scroll");
            existItem.removeItem(1);
            inventory.put("scroll", existItem);
            Location village = locations.get(0);

            Portal portal = village.getPortal();
            Pane root = Flonarnia.foregroundRoot;
            if (!village.isCreated()){
                root.getChildren().clear();
                Flonarnia.flobjects.clear();

                for (Location location : locations)
                    location.setCreated(false);
                village.setCreated(true);
                Flonarnia.flobjects.addAll(village.createContext());
                Flonarnia.flobjects.add(Flonarnia.player);
                root.getChildren().addAll(Flonarnia.flobjects);
                root.getChildren().add(portal);
            }
            toLocation(portal, "Dion", 0);
        }
    }
    public void toLocation(Portal portal, String location, int cost){
        if (portal != null && shekelAmount.get() - cost >= 0){
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
            Flonarnia.logPanel.addLine(String.format("Welcome to %s!", location), Color.GREEN);
            shekelAmount.set(shekelAmount.get() - cost);
            Flonarnia.teleportPanel.setVisible(false);
        }
    }
    public void changeSword(InventoryItem sword){
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
            if (addToInventory(item) && item.kind.compareTo("weapon") == 0)
                return;
            shekelAmount.set(shekelAmount.get() - cost);
        }
    }

    public boolean addToInventory(InventoryItem item){
        InventoryItem existItem = inventory.get(item.name);
        if (existItem != null){
            System.out.println("existed");
            inventory.remove(item.name);
            existItem.addItem(item.Amount.get());
            inventory.put(item.name, existItem);
            return true;
        }
        else{
            System.out.println("null. Create");
            inventoryPanel.addItem(item);
            inventory.put(item.name, item);
            return false;
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
                enduranceCapacity.set(enduranceCapacity.get() - 1);
                value *= 2;
            }
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
                enduranceCapacity.set(enduranceCapacity.get() - 1);
                value *= 2;
            }
        }
        super.moveY(value);
    }

    public void attack(){
        if (sword == null || manaCapacity.getValue() - power / 10 <= 0)
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
            if (manaCapacity.getValue() - power / 10 > 0)
                manaCapacity.set(manaCapacity.getValue() - power / 10);
            else
                manaCapacity.set(0);
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
                        shekelAmount.set(shekelAmount.getValue() + enemy.cost.get());
                        exp += enemy.cost.get();
                        while (exp > maxExp){
                            levelUp();
                        }
                    }
                }
            }

            changeSize(WIDTH, HEIGHT);
            imageView.setScaleX(1);
            animation.interpolate(0);
        });
    }
    private void levelUp(){
        exp -= maxExp;
        maxExp += 100;
        if (level.get() + 1 <= 80)
            level.set(level.get() + 1);
        else
            return;
        healthCapacity.set(level.get() * 99);
        healthMaxCapacity.set(level.get() * 99);

        manaCapacity.set(level.get() * 61);
        manaMaxCapacity.set(level.get() * 61);

        enduranceCapacity.set(level.get() * 3 / 2  + 100);
        enduranceMaxCapacity.set(level.get() * 3 / 2  + 100);
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
    public void attacked(double value, int power, boolean X){
        double sign = value / Math.abs(value);
        if (healthCapacity.get() - power > 0)
            healthCapacity.set(healthCapacity.get() - power);
        else {
            if(!X) {
                moveX(-0.0001);
                this.setRotate(89);
            }
            else
                this.setRotate(sign * 89);
            this.kill();
        }

        AttackedTransition attackedTransition;

        double throwLen = 20;
        if (!X && sign > 0)
            throwLen *= 3;
        if (X){
            this.setTranslateX(this.getTranslateX() + sign * throwLen);
            attackedTransition = new AttackedTransition(Duration.millis(1000), this, this.getTranslateX() + value, X);
            attackedTransition.play();
        }
        else{
            this.setTranslateY(this.getTranslateY() + sign * throwLen);
            attackedTransition = new AttackedTransition(Duration.millis(1000), this, this.getTranslateY() + value, X);
            attackedTransition.play();
        }

        ColorAdjust blackout = new ColorAdjust();

        blackout.setContrast(0.2);
        blackout.setHue(-0.25);
        blackout.setBrightness(0);
        blackout.setSaturation(0.5);

        imageView.setEffect(blackout);
        imageView.setCache(true);
        imageView.setCacheHint(CacheHint.SPEED);

        attackedTransition.setOnFinished(event -> {
            if (isAlive())
                imageView.setEffect(null);
            this.setTranslateY((int)this.getTranslateY());
            this.setTranslateX((int)this.getTranslateX());
        });
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
        if (isAlive()) {
            int manaValue = 3;
            int healthValue = 3;
            int enduranceValue = 7;
            regenerateHP(healthValue);
            regenerateMP(manaValue);
            regenerateED(enduranceValue);
        }
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

    public boolean isAlive(){
        return alive;
    }
    private void kill(){
        if (alive) {
            if (level.get() > 1)
                level.set(level.get() - 1);
            alive = false;
            canMove = false;
            healthCapacity.set(0);
            manaCapacity.set(0);
            enduranceCapacity.set(0);
            Flonarnia.toVillagePanel.setVisible(true);
            Flonarnia.logPanel.addLine("You are dead", Color.RED);
        }
    }
    public void resurrect(){
        alive = true;
        healthCapacity.set(1);
        manaCapacity.set(1);
        enduranceCapacity.set(1);
        imageView.setEffect(null);
        Flonarnia.logPanel.addLine("You were resurrected", Color.GREEN);
        this.setRotate(0);
    }

    public void saveState(String login, String parentPath){
        try(DataOutputStream file =
                    new DataOutputStream(new FileOutputStream(parentPath + "\\Base\\" + login + ".bin")))
        {
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
    public void loadState(String login, String parentPath){
        try(DataInputStream file =
                    new DataInputStream(new FileInputStream(parentPath + "\\Base\\" + login + ".bin")))
        {
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
