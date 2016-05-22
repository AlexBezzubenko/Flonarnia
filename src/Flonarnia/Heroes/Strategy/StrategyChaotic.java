package Flonarnia.Heroes.Strategy;

import Flonarnia.Heroes.Enemy;
import Flonarnia.Heroes.MovableFlobject;
import Flonarnia.Heroes.NPC;
import Flonarnia.Scenes.Flonarnia;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

/**
 * Created by Alexander on 10.05.2016.
 */
public class StrategyChaotic extends Strategy {
    private int moveCircleRadius = 100;
    private double startX;
    private double startY;

    private int direction = 1;
    private int distance = 0;

    private int velocity = 1;
    private final Random random = new Random();

    public StrategyChaotic(MovableFlobject caller) {
        super(caller);
        startX = caller.getTranslateX();
        startY = caller.getTranslateY();

        Rectangle rect = new Rectangle(moveCircleRadius * 2, moveCircleRadius * 2, Color.TRANSPARENT);
        rect.setTranslateX(startX - moveCircleRadius);
        rect.setTranslateY(startY - moveCircleRadius);
        rect.setStrokeWidth(3);
        rect.setStroke(Color.BLACK);
        //Flonarnia.gameRoot.getChildren().addAll(rect);
    }

    public void move() {
        if (caller.getClass() == NPC.class && Flonarnia.player.getTarget() != this.caller)
            stopped = false;
        double px = Flonarnia.player.getTranslateX();
        double py = Flonarnia.player.getTranslateY();


        double x = caller.getTranslateX();
        double y = caller.getTranslateY();

        if (Math.abs(px - x) < activateRadius && Math.abs(py - y) < activateRadius)
            if (caller.getClass() == Enemy.class && Flonarnia.player.isAlive())
             caller.setContext(new StrategyAttack(caller));
        if (!stopped) {
            switch (outOfBorder()) {
                case 1:
                    direction = 3;
                    break;
                case 2:
                    direction = 4;
                    break;
                case 3:
                    direction = 1;
                    break;
                case 4:
                    direction = 2;
                    break;
                case 0:
                    break;
            }
            if (distance == 0) {
                direction = random.nextInt(4) + 1;
                distance = random.nextInt(20) + moveCircleRadius + 20;
            }

            switch (direction) {
                case 1:
                    caller.moveX(velocity);
                    break;
                case 2:
                    caller.moveY(velocity);
                    break;
                case 3:
                    caller.moveX(-velocity);
                    break;
                case 4:
                    caller.moveY(-velocity);
                    break;
            }
            distance--;
        }
    }


    private int outOfBorder() {
        double x = caller.getTranslateX();
        double y = caller.getTranslateY();

        double w = caller.getWidth();
        double h = caller.getHeight();

        if (x + velocity > startX + moveCircleRadius - w)
            return 1;

        if (y + velocity > startY + moveCircleRadius - h)
            return 2;

        if(x - velocity < startX - moveCircleRadius)
            return 3;

        if(y - velocity < startY - moveCircleRadius)
            return 4;

        return 0;
    }
}
