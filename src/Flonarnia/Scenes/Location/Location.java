package Flonarnia.Scenes.Location;

import Flonarnia.Flobjects.Flobject;
import Flonarnia.Flobjects.Portal;

import javax.sound.sampled.Port;
import java.util.ArrayList;

public abstract class Location {
    protected ArrayList<Flobject> flobjects = new ArrayList<>();
    protected Portal portal;
    protected String name;
    protected int cost;

    public boolean created = false;
    public Location(){}
    public Location(String name, int cost){
        this.name = name;
        this.cost = cost;
    }
    public ArrayList<Flobject> createContext(){
        return flobjects;
    }
    public Portal getPortal(){
        return portal;
    }
    public String getName(){return name;}
    public int getCost(){return cost;}

}
