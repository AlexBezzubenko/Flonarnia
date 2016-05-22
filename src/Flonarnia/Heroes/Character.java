package Flonarnia.Heroes;

import Flonarnia.Scenes.Flonarnia;
import Flonarnia.tools.Collision;

/**
 * Created by Alexander on 15.04.2016.
 */

public class Character extends MovableFlobject {
    protected boolean run = false;

    public Character(double translateX, double translateY, String name) {
        super(translateX, translateY, "NPC", name);
    }

    public void setRun(boolean run){
        this.run = run;
    }
    public boolean getRun(){
        return run;
    }
    public void moveX(double value){
        this.setTranslateX(this.getTranslateX() + value);

        if (Collision.checkTranslateX(this, Flonarnia.flobjects, value) != null){
            return;
        }

        double x = offsetX;
        int c = columns;
        if (run){
            x += 212;
        }
        if (isRunning){
            x += width * 2 ;
            c -= 2;
        }

        if (value > 0) {
            animation.setOffsetY(offsetY + 192);
        } else {
            animation.setOffsetY(offsetY + 128);
        }

        animation.setOffsetX(x);
        animation.setColumns(c);
        animation.play();
        animation.onFinishedProperty().set(actionEvent -> isRunning = true);
    }

    public void moveY(double value) {
        animation.setWidthHeight(width,height);
        this.setTranslateY(this.getTranslateY() + value);
        if (Collision.checkTranslateY(this, Flonarnia.flobjects, value) != null){
            return;
        }

        double x = offsetX;
        int c = columns;
        if (run){
            x += 212;
        }
        if (isRunning){
            x += width * 2 ;
            c -= 2;
        }
        if (value > 0) {
            animation.setOffsetY(offsetY + 0);
        } else {
            animation.setOffsetY(offsetY + 63);
        }

        animation.setOffsetX(x);
        animation.setColumns(c);
        animation.play();
        animation.onFinishedProperty().set(actionEvent -> isRunning = true);
    }

    public void stop(){
        if (isRunning) {
            isRunning = false;
            animation.setColumns(6);
            animation.setOffsetX(offsetX);
        }
        animation.interpolate(0);
        animation.stop();
    }
}
