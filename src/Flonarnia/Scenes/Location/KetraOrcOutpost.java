package Flonarnia.Scenes.Location;

import Flonarnia.Flobjects.*;
import Flonarnia.Heroes.Enemy;
import Flonarnia.Heroes.Strategy.StrategyChaotic;

import java.util.ArrayList;


/**
 * Created by Alexander on 17.05.2016.
 */
public class KetraOrcOutpost extends Location{
    public KetraOrcOutpost(){
        super("Ketra Orc Outpost", 500, 1);
    }

    @Override
    public ArrayList<Flobject> createContext(){
        ArrayList<Flobject> flobjects = new ArrayList<>();

        int rowCount = 5;
        int columnCount = 5;

        for (int i = 0; i < rowCount * columnCount; i++){
            if (i == 12)
                continue;
            double x = (i % columnCount) * 400;
            double y = (i / columnCount) * 400;

            Enemy ogre = new Enemy(x, y, "Ogre", level);
            //ogre.setContext(new StrategyAttack(ogre));
            ogre.setContext(new StrategyChaotic(ogre));
            flobjects.add(ogre);
        }

        flobjects.add(new MountainH(-200, -350));
        flobjects.add(new MountainH(800, -350));
        flobjects.add(new MountainV(1800, -350));
        flobjects.add(new MountainV(1800, 450));
        flobjects.add(new MountainV(1800, 1250));
        flobjects.add(new MountainH(-200, 1800));
        flobjects.add(new MountainH(800, 1800));
        flobjects.add(new MountainV(-450, -350));
        flobjects.add(new MountainV(-450, 450));
        flobjects.add(new MountainV(-450, 1250));

        portal = new Portal(700, 800);
        return flobjects;
    }
}
