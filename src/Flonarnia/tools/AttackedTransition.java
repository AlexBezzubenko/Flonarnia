package Flonarnia.tools;

import Flonarnia.Heroes.Player;
import Flonarnia.Scenes.Flonarnia;
import javafx.animation.Transition;
import javafx.util.Duration;

/**
 * Created by Alexander on 22.05.2016.
 */
public class AttackedTransition extends Transition {
    protected double startTranslate;
    protected double newTranslate;
    protected double translateDiff;
    protected Player player;
    protected boolean X;

    public AttackedTransition(Duration duration, Player player,  double newTranslate, boolean X ) {
        setCycleDuration(duration);
        this.player = player;
        this.newTranslate = newTranslate;
        if (X)
            this.startTranslate = player.getTranslateX();
        else
            this.startTranslate = player.getTranslateY();
        this.translateDiff = newTranslate - startTranslate;
        this.X = X;
    }

    @Override
    public void interpolate(double fraction) {
        if(X) {
            player.setTranslateX(startTranslate + (translateDiff * fraction));
            if (Collision.checkTranslateX(player, Flonarnia.flobjects, 0) != null) {
                player.setTranslateX(startTranslate + (translateDiff * (fraction - 0.1)));
                this.stop();
                return;
            }
        }else{
            player.setTranslateY(startTranslate + (translateDiff * fraction));
            if (Collision.checkTranslateY(player, Flonarnia.flobjects, 0) != null) {
                player.setTranslateY(startTranslate + (translateDiff * (fraction - 0.1)));
                this.stop();
                return;
            }
        }
    }

}
