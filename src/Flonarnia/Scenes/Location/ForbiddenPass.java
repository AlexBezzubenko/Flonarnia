package Flonarnia.Scenes.Location;

import Flonarnia.Flobjects.Flobject;
import Flonarnia.Flobjects.MountainH;
import Flonarnia.Flobjects.MountainV;
import Flonarnia.Flobjects.Portal;
import Flonarnia.Heroes.Enemy;
import Flonarnia.Heroes.Strategy.StrategyAttack;

import java.util.ArrayList;

/**
 * Created by Alexander on 17.05.2016.
 */
public class ForbiddenPass extends Location {
    public ForbiddenPass(){
        super("the Forbidden Pass", 2000, 100);
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
            String enemyName = "Ogre";

            if (i >= 0 && i < 5)
                enemyName = "Ogre";
            else if(i >= 5 && i < 10)
                enemyName = "Buffalo";
            else if(i >= 0 && i < 15)
                enemyName = "Dark Soul";
            else if(i >= 0 && i < 20)
                enemyName = "Undead";
            else if(i >= 0 && i < 25)
                enemyName = "Dragon";


            Enemy enemy = new Enemy(x, y, enemyName, level);
            enemy.setContext(new StrategyAttack(enemy));
            flobjects.add(enemy);
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
