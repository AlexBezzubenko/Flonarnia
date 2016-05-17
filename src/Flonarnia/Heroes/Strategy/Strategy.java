package Flonarnia.Heroes.Strategy;

import Flonarnia.Heroes.MovableFlobject;

/**
 * Created by Alexander on 10.05.2016.
 */
public abstract class Strategy {
    protected MovableFlobject caller;
    protected int activateRadius = 200;
    protected boolean stopped = false;

    public Strategy(MovableFlobject caller){
        this.caller = caller;
    }
    public void move(){}

    public void pause(boolean pause){
        stopped = pause;
    }
}
