package Flonarnia.Heroes.Strategy;

import Flonarnia.Heroes.MovableFlobject;
import Flonarnia.Scenes.Flonarnia;
import javafx.animation.AnimationTimer;

import java.util.Random;

public class StrategyNPCBeforeHouse extends Strategy {
    private int moveCircleRadius = 100;
    private double startX;

    private int direction = 1;
    private int distance = 0;

    private int velocity = 1;
    private final Random random = new Random();

    public StrategyNPCBeforeHouse(MovableFlobject caller) {
        super(caller);
        startX = caller.getTranslateX();
    }

    public void move() {
        if (Flonarnia.player.getTarget() != this.caller)
            stopped = false;
        if(!stopped) {
            switch (outOfBorder()) {
                case 1:
                    direction = 2;
                    break;
                case 2:
                    direction = 1;
                    break;
                case 0:
                    break;
            }
            if (distance == 0) {
                direction = random.nextInt(2) + 1;
                distance = random.nextInt(40) + moveCircleRadius - 40;
            }

            switch (direction) {
                case 1:
                    caller.moveX(-velocity);
                    break;
                case 2:
                    caller.moveX(velocity);
                    break;
            }
            distance--;
        }
    }


    private int outOfBorder() {
        double x = caller.getTranslateX();
        double y = caller.getTranslateY();

        double w = caller.getBounds().getWidth();

        if (x + velocity > startX + moveCircleRadius - w)
            return 2;

        if (x - velocity < startX - moveCircleRadius)
            return 1;
        return 0;
    }
}

