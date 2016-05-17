package Flonarnia.Heroes.Strategy;

import Flonarnia.Heroes.MovableFlobject;
import Flonarnia.Scenes.Flonarnia;

/**
 * Created by Alexander on 10.05.2016.
 */
public class StrategyAttack extends Strategy{
    public StrategyAttack(MovableFlobject caller){
        super(caller);
    }
    public void move(){
        double px = Flonarnia.player.getBounds().getTranslateX();
        double py = Flonarnia.player.getBounds().getTranslateY();

        double x = caller.getBounds().getTranslateX();
        double y = caller.getBounds().getTranslateY();

        if (Math.abs(px - x) > activateRadius * 1.5 || Math.abs(py - y) > activateRadius * 1.5)
            //caller.setContext(new StrategyChaotic(caller));
            caller.setContext(new StrategyComeBack(caller));


        double deltaX = x - px;
        double deltaY = y - py;

        double velocity = 1;

        boolean moveX = Math.abs(deltaX) <= Math.abs(deltaY);

        if ((Math.abs(deltaX) < velocity || !moveX) && deltaY != 0){
            double a = -velocity * (deltaY / Math.abs(deltaY));
            System.out.println("deltaX = 0 " + a + "  " + deltaY);
            System.out.println(Flonarnia.player.getTranslateX() + " " + Flonarnia.player.getTranslateY());
            caller.moveY(a);
            return;
        }
        if ((Math.abs(deltaY) < velocity || moveX) && deltaX != 0){
            double a = -velocity * (deltaX / Math.abs(deltaX));
            System.out.println("deltaY = 0 " + a + "  " + deltaX);
            caller.moveX(a);
            return;
        }
    }
}
