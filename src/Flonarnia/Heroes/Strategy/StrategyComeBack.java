package Flonarnia.Heroes.Strategy;

import Flonarnia.Heroes.Enemy;
import Flonarnia.Heroes.MovableFlobject;
import Flonarnia.Scenes.Flonarnia;

/**
 * Created by Alexander on 17.05.2016.
 */
public class StrategyComeBack extends Strategy{
    public StrategyComeBack(MovableFlobject caller){
        super(caller);
    }
    public void move(){
        double px = Flonarnia.player.getBounds().getTranslateX();
        double py = Flonarnia.player.getBounds().getTranslateY();

        double x = caller.getBounds().getTranslateX();
        double y = caller.getBounds().getTranslateY();

        double startX = caller.getStartTranslteX();
        double startY = caller.getStartTranslteY();

        if (Math.abs(px - x) < activateRadius && Math.abs(py - y) < activateRadius)
            if (caller.getClass() == Enemy.class)
                caller.setContext(new StrategyAttack(caller));

        double deltaX = x - startX;
        double deltaY = y - startY;
        double velocity = 1;

        if (Math.abs(deltaX) < velocity && Math.abs(deltaY) < velocity)
            caller.setContext(new StrategyChaotic(caller));

        boolean moveX = Math.abs(deltaX) <= Math.abs(deltaY);

        if ((Math.abs(deltaX) < velocity || !moveX) && deltaY != 0){
            double a = -velocity * (deltaY / Math.abs(deltaY));
            System.out.println(Flonarnia.player.getTranslateX() + " " + Flonarnia.player.getTranslateY());
            caller.moveY(a);
            return;
        }
        if ((Math.abs(deltaY) < velocity || moveX) && deltaX != 0){
            double a = -velocity * (deltaX / Math.abs(deltaX));
            caller.moveX(a);
            return;
        }
    }
}
