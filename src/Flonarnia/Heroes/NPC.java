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
            System.out.println("Changed target: " + this.getClass().getSimpleName());
            Flonarnia.player.changeTarget(this);
            Flonarnia.targetPanel.changeTarget(species, this.getClass().getSimpleName());
            switch (species){
                case "trader":
                    if (Flonarnia.player != null) {
                        Flonarnia.tradePanel.createPoisonTrade();
                        Flonarnia.tradePanel.setVisible(true);
                    }
                    break;
                case "warrior":
                    if (Flonarnia.player != null) {
                        Flonarnia.tradePanel.createWeaponTrade();
                        Flonarnia.tradePanel.setVisible(true);
                    }
                    break;
            }
        });
    }
}

