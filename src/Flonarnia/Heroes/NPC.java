package Flonarnia.Heroes;

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
    }

    public void moveX(int value){
        super.moveX(value);
    }
    public void moveY(int value){
        super.moveY(value);
    }

    @Override
    protected void setTarget(){
        this.setOnMouseClicked(event -> {
            Flonarnia.player.changeTarget(this);
            Flonarnia.targetPanel.changeTarget(this, species, this.getClass().getSimpleName());

            double deltaX = this.getTranslateX() - Flonarnia.player.getTranslateX();
            double deltaY = this.getTranslateY() - Flonarnia.player.getTranslateY();

            double activateRadius = 200;
            if (Math.abs(deltaX) < activateRadius || Math.abs(deltaY) < activateRadius) {
                if (this.getContext() != null)
                    this.getContext().pause(true);
                if (Math.abs(deltaX) > Math.abs(deltaY))
                    this.moveX(-0.001 * deltaX / Math.abs(deltaX));
                else
                    this.moveY(-0.001 * deltaY / Math.abs(deltaY));
                this.stop();
                switch (species) {
                    case "Trader":
                        Flonarnia.tradePanel.createPoisonTrade();
                        Flonarnia.tradePanel.setVisible(true);
                        break;
                    case "Warrior":
                        Flonarnia.tradePanel.createWeaponTrade();
                        Flonarnia.tradePanel.setVisible(true);
                        break;
                    case "GateKeeper":
                        Flonarnia.teleportPanel.setVisible(true);
                        break;
                    case "Shaman":
                        Flonarnia.shamanPanel.setVisible(true);
                        break;
                }
            }
        });
    }
}

