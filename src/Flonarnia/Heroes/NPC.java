package Flonarnia.Heroes;

import Flonarnia.Panels.TradePanel;
import Flonarnia.Scenes.Flonarnia;
import java.lang.*;

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
        this.setOnMouseClicked(event -> {
             setTarget();
             System.out.println("NPC Clicked");
        });
    }

    public void moveX(int value){
        super.moveX(value,false);
    }
    public void moveY(int value){
        super.moveY(value, false);
    }

    @Override
    protected void setTarget(){
        this.setOnMouseClicked(event -> {
            Flonarnia.player.changeTarget(this);
            String name = species.toLowerCase();
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            Flonarnia.targetPanel.changeTarget(name);
        });

        switch (species){
            case "trader":
                TradePanel tradePanel = new TradePanel(300, 300, Flonarnia.appRoot);
        }
    }
}

