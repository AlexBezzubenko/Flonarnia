package Flonarnia.Heroes.Strategy;

import Flonarnia.Heroes.Enemy;
import Flonarnia.Scenes.Flonarnia;

/**
 * Created by Alexander on 10.05.2016.
 */
public class StrategyAttack extends Strategy{
    public StrategyAttack(Enemy caller){
        super(caller);
    }
    public void move(){
        double px = Flonarnia.player.getBounds().getTranslateX();
        double py = Flonarnia.player.getBounds().getTranslateY();

        double x = caller.getBounds().getTranslateX();
        double y = caller.getBounds().getTranslateY();

        if (Math.abs(px - x) > activateRadius * 2  || Math.abs(py - y) > activateRadius * 2)
            caller.setContext(new StrategyChaotic(caller));


        double deltaX = x - px;
        double deltaY = y - py;

        double velocity = 1;


        boolean moveX = Math.abs(deltaX) <= Math.abs(deltaY);

        if ((Math.abs(deltaX) < velocity || !moveX) && deltaY != 0){
            double a = -velocity * (deltaY / Math.abs(deltaY));
            System.out.println("deltaX = 0 " + a + "  " + deltaY);
            caller.moveY(a);
            return;
        }
//        if ((deltaY == 0 || moveX) && deltaX != 0){
        if ((Math.abs(deltaY) < velocity || moveX) && deltaX != 0){
            double a = -velocity * (deltaX / Math.abs(deltaX));
            System.out.println("deltaY = 0 " + a + "  " + deltaX);
            caller.moveX(a);
            return;
        }
        /*if (Math.abs(deltaX) < -velocity){
            int a = -1 * Math.abs((int)deltaX) * (int)(deltaY / Math.abs(deltaY));
            System.out.println("deltaX = 0 " + a);
            caller.moveY(a);
            return;
        }
        if (Math.abs(deltaY) < -velocity){
            int a = -1 * Math.abs((int)deltaY) * (int)(deltaX / Math.abs(deltaX));
            System.out.println("deltaY = 0 " + a);
            caller.moveX(a);
            return;
        }*/
    }
}
