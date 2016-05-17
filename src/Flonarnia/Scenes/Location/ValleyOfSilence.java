package Flonarnia.Scenes.Location;

import Flonarnia.Flobjects.Flobject;
import Flonarnia.Flobjects.Portal;
import Flonarnia.Heroes.Enemy;
import Flonarnia.Heroes.Strategy.StrategyAttack;

import java.util.ArrayList;

/**
 * Created by Alexander on 17.05.2016.
 */
public class ValleyOfSilence extends Location {
    public ValleyOfSilence(){
        super("Valley of Silence", 1000);
    }

    @Override
    public ArrayList<Flobject> createContext(){
        flobjects.clear();

        int rowCount = 3;
        int columnCount = 3;

        for (int i = 0; i < rowCount * columnCount; i++){
            double x = (i % columnCount) * 300 + 3000;
            double y = (i / columnCount) * 300 + 600;

            Enemy ogre = new Enemy(x, y, "Ogre");
            ogre.setContext(new StrategyAttack(ogre));
            flobjects.add(ogre);
        }

        portal = new Portal(3000, 1800);
        return flobjects;
    }
}
