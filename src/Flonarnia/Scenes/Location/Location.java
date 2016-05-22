package Flonarnia.Scenes.Location;

import Flonarnia.Flobjects.Flobject;
import Flonarnia.Flobjects.Portal;

import java.util.ArrayList;

public abstract class Location {
    protected Portal portal;
    protected String name;
    protected int cost;
    protected int level = 1;

    protected boolean created = false;

    public Location(String name, int cost, int level){
        this.name = name;
        this.cost = cost;
        this.level = level;
    }
    public abstract ArrayList<Flobject> createContext();
    public Portal getPortal(){
        return portal;
    }
    public String getName(){return name;}
    public int getCost(){return cost;}
    public int getLevel(){return level;}
    public void setCreated(boolean created){
        this.created = created;
    }
    public boolean isCreated(){return created;}
}
