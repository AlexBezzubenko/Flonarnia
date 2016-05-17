package Flonarnia.Panels;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by Alexander on 30.04.2016.
 */
public class InventoryItem {
    public String name;
    public String kind;
    private int amount = 0;
    public int cost = 0;

    public SimpleIntegerProperty Amount = new SimpleIntegerProperty();

    public InventoryItem(String name, String kind){
        this.name = name;
        this.kind = kind;
    }

    public InventoryItem(String name, String kind, int cost){
        this(name, kind);
        this.cost = cost;
    }

    public void addItem(int value){
        if (amount + value < 1000)
            amount += value;
        else
            amount = 999;
        Amount.set(amount);
    }
    public void removeItem(int value){
        if (amount - value > 0)
            amount -= value;
        else
            amount = 0;
        Amount.set(amount);
    }
}
