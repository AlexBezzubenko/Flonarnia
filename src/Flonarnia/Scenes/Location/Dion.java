package Flonarnia.Scenes.Location;

import Flonarnia.Flobjects.*;
import Flonarnia.Heroes.NPC;
import Flonarnia.Heroes.Strategy.StrategyChaotic;
import Flonarnia.Heroes.Strategy.StrategyNPCBeforeHouse;

import java.util.ArrayList;

public class Dion extends Location{
    public Dion(){
        super("Dion", 1, 1);
    }

    @Override
    public ArrayList<Flobject> createContext(){
        ArrayList<Flobject> flobjects = new ArrayList<>();

        //NPC guider = new NPC(2000, 514, "Guider");
        NPC warrior = new NPC(575, 510, "Warrior");
        NPC shaman = new NPC(1075, 510, "Shaman");
        NPC trader = new NPC(825, 510, "Trader");
        NPC gatekeeper = new NPC(870, 600, "GateKeeper");
        NPC blacksmith = new NPC(1100, 1001, "Blacksmith");


        shaman.setContext(new StrategyNPCBeforeHouse(shaman));
        warrior.setContext(new StrategyNPCBeforeHouse(warrior));
        trader.setContext(new StrategyChaotic(trader));
        //guider.setContext(new StrategyChaotic(guider));

        Flobject[] houses = new Flobject[]{
                new TypedHouse(500, 200, "house_type_1"),
                new TypedHouse(750, 200, "house_type_2"),
                new TypedHouse(1000, 200, "house_type_3"),
                //new TypedHouse(2500, 200, "house_type_4")
        };

        for (Flobject f: houses){
            flobjects.add(f);
        }
        flobjects.add(new Tree(300, 500));
        flobjects.add(new Tree(350, 800));
        flobjects.add(new Tree(800, 1000));
        flobjects.add(new Tree(1000, 800));
        flobjects.add(new Tree(1150, 500));
        flobjects.add(new Tree(1450, 500));
        flobjects.add(new Tree(1200, 900));
        flobjects.add(new Tree(1500, 700));
        flobjects.add(new Tree(1600, 850));


        flobjects.add(trader);
        //flobjects.add(guider);
        flobjects.add(warrior);
        flobjects.add(shaman);
        flobjects.add(gatekeeper);
        flobjects.add(blacksmith);

        flobjects.add(new MountainH(-250, -100));
        flobjects.add(new MountainH(750, -100));
        flobjects.add(new MountainV(1750, -100));
        flobjects.add(new MountainV(1750, 700));
        flobjects.add(new MountainH(-250, 1250));
        flobjects.add(new MountainH(750, 1250));
        flobjects.add(new MountainV(-250, -100));
        flobjects.add(new MountainV(-250, 700));

        flobjects.add(new Fire(700, 550));
        flobjects.add(new Fire(900, 550));

        portal = new Portal(750, 600);

        return flobjects;
    }
}
