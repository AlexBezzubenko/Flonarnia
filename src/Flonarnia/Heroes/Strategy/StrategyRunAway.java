package Flonarnia.Heroes.Strategy;

import Flonarnia.Heroes.Enemy;
import Flonarnia.Scenes.Flonarnia;

/**
 * Created by Alexander on 10.05.2016.
 */
public class StrategyRunAway extends Strategy {
    public StrategyRunAway(Enemy caller){
        super(caller);
    }

    public void move(){
        double velocity = 5;

        double px = Flonarnia.player.getTranslateX();
        double py = Flonarnia.player.getTranslateY();

        double x = caller.getTranslateX();
        double y = caller.getTranslateY();

        /*if (Math.abs(px - x) > activateRadius || Math.abs(py - y) > activateRadius)
            return;*/

        double deltaX = x - px;
        double deltaY = y - py;

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            if (deltaX > 0)
                caller.moveX(velocity);
            else
                caller.moveX(-velocity);
        }
        else {
            if (deltaY > 0)
                caller.moveY(velocity);
            else
                caller.moveY(-velocity);
        }
    }
}
