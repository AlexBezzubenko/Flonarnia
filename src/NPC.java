import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

/**
 * Created by Alexander on 15.04.2016.
 */

//        name = "guider";
//        name = "warrior";
//        name = "shaman";
//        name = "trader";
//        name = "blacksmith";
//        name = "gatekeeper";
public class NPC  extends Character {
    public NPC(double translateX, double translateY, String name){
        super(translateX, translateY, name);
    }

    public void moveX(int value){
        super.moveX(value,false);
    }
    public void moveY(int value){
        super.moveY(value, false);
    }
}

