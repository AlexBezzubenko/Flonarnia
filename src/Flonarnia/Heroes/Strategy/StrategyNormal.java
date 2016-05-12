package Flonarnia.Heroes.Strategy;
import Flonarnia.Heroes.Enemy;
import javafx.animation.AnimationTimer;

/**
 * Created by Alexander on 10.05.2016.
 */
public class StrategyNormal extends Strategy{
    private boolean right = true;
    private boolean top = false;
    private boolean left = false;
    private boolean bottom = false;
    private double moveCircleRadius = 50;
    private double startX;
    private double startY;

    public StrategyNormal(Enemy caller){
        super(caller);
        startX = caller.getTranslateX();
        startY = caller.getTranslateY();
    }
    public void move(){
        double x = caller.getTranslateX();
        double y = caller.getTranslateY();

        double w = caller.getWidth();
        double h = caller.getHeight();

        int velocity = 3;

        if(x > startX + moveCircleRadius - w) {
            right = false;
            bottom = true;
        }
        if(x < startX - moveCircleRadius) {
            left = false;
            top = true;
        }
        if(y > startY + moveCircleRadius - h) {
            bottom = false;
            left = true;
        }
        if(y < startY - moveCircleRadius) {
            top = false;
            right = true;
        }

        if (right){
            caller.moveX(velocity);
        }
        if(left){
            caller.moveX(-velocity);
        }
        if (top){
            caller.moveY(-velocity);
        }
        if(bottom){
            caller.moveY(velocity);
        }
    }
}
