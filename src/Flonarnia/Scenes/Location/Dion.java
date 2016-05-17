package Flonarnia.Scenes.Location;

import Flonarnia.Flobjects.*;
import Flonarnia.Heroes.Enemy;
import Flonarnia.Heroes.NPC;
import Flonarnia.Heroes.Strategy.StrategyAttack;
import Flonarnia.Heroes.Strategy.StrategyChaotic;
import Flonarnia.Heroes.Strategy.StrategyNPCBeforeHouse;

import java.util.ArrayList;

public class Dion extends Location{
    public Dion(){
        super("Dion", 1000);
    }

    @Override
    public ArrayList<Flobject> createContext(){
        flobjects.clear();

        NPC guider = new NPC(2000, 514, "Guider");
        NPC warrior = new NPC(2200, 513, "Warrior");
        NPC shaman = new NPC(2400, 510, "Shaman");
        NPC trader = new NPC(2600, 511, "Trader");
        NPC gatekeeper = new NPC(2230, 600, "GateKeeper");
        NPC blacksmith = new NPC(1100, 1001, "Blacksmith");
        Enemy buffalo = new Enemy(1700, 705, "Buffalo");
        Enemy dark_soul = new Enemy(1100, 704, "Dark Soul");
        dark_soul.setContext(new StrategyAttack(dark_soul));
        Enemy dragon = new Enemy(1200, 703, "Dragon");
        dragon.setContext(new StrategyAttack(dragon));
        Enemy ogre = new Enemy(1400, 702, "Ogre");
        ogre.setContext(new StrategyAttack(ogre));
        Enemy undead = new Enemy(1300, 701, "Undead");
        undead.setContext(new StrategyAttack(undead));


        shaman.setContext(new StrategyNPCBeforeHouse(shaman));
        warrior.setContext(new StrategyNPCBeforeHouse(warrior));
        trader.setContext(new StrategyChaotic(trader));
        guider.setContext(new StrategyChaotic(guider));

        Flobject[] houses = new Flobject[]{
                new TypedHouse(1900, 201, "house_type_1"),
                new TypedHouse(2100, 202, "house_type_2"),
                new TypedHouse(2300, 203, "house_type_3"),
                new TypedHouse(2500, 204, "house_type_4")
        };

        for (Flobject f: houses){
            flobjects.add(f);
        }
        flobjects.add(new Tree(500, 401));
        flobjects.add(new Tree(500, 600));
        flobjects.add(new Tree(800, 402));
        flobjects.add(new Tree(1000, 500));
        flobjects.add(new House(-100, -100));
        flobjects.add(trader);
        flobjects.add(guider);
        flobjects.add(warrior);
        flobjects.add(shaman);
        flobjects.add(gatekeeper);
        flobjects.add(blacksmith);
        flobjects.add(dragon);
        flobjects.add(dark_soul);
        flobjects.add(buffalo);
        flobjects.add(ogre);
        flobjects.add(undead);
        flobjects.add(new Fire(2050, 550));
        flobjects.add(new Fire(2250, 550));

        for (int i = 0; i < 5; i++){
            Enemy buffalo2 = new Enemy(2000 + i * 300, 600 + i, "Buffalo");
            if (i > 2) {
                buffalo2.setContext(new StrategyAttack(buffalo2));
            }
            flobjects.add(buffalo2);
        }
        portal = new Portal(2100, 600);
        return flobjects;
    }
}
