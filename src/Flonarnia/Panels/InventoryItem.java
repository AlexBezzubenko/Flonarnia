package Flonarnia.Panels;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by Alexander on 30.04.2016.
 */
public class InventoryItem {
    public String name;
    public String kind;
    public int amount = 0;
    public int cost = 0;
    public SimpleIntegerProperty amountS = new SimpleIntegerProperty();

    public InventoryItem(String name, String kind){
        this.name = name;
        this.kind = kind;
    }
    public InventoryItem(String name, String kind, int cost){
        this(name, kind);
        this.cost = cost;
    }
    //public InventoryItem(String name, String kind, int amount){
    //    this(name, kind);
    //    this.amount = amount;
    //}

    public void addItem(int value){
        if (amount + value < 1000) {
            amount += value;
            amountS.set(amount);
        }else
            amount = 999;
    }
    public void addItem(){
        if (amount + 1 < 1000)
            amount++;
        else
            amount = 999;
    }
    public void removeItem(int value){
        if (amount - value > 0)
            amount -= value;
        else
            amount = 0;
    }
    public void removeItem(){
        if (amount - 1 > 0)
            amount--;
        else
            amount = 0;
    }
}