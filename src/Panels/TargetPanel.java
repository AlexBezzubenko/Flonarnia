package Panels;
/**
 * Created by Alexander on 23.04.2016.
 */
public class TargetPanel extends Panel {
    public TargetPanel(double translateX, double translateY){
        super(translateX, translateY);
    }
    public void changeTarget(String name){
        this.setVisible(true);
        this.label.setText(name);
    }
}
