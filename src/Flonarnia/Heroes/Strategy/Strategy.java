package Flonarnia.Heroes.Strategy;

import Flonarnia.Heroes.Enemy;

/**
 * Created by Alexander on 10.05.2016.
 */
public abstract class Strategy {
    protected Enemy caller;
    protected int activateRadius = 200;

    public Strategy(Enemy caller){
        this.caller = caller;
    }
    public void move(){};
}
