package Flonarnia.tools;

import Flonarnia.Flobjects.Flobject;
import java.util.ArrayList;

/**
 * Created by Alexander on 13.04.2016.
 */
public class Collision {
    public static Flobject checkTranslateX(Flobject checking, ArrayList<Flobject> checkers, double value){
        for (Flobject flobject:checkers){
            if(flobject != checking && flobject.getBounds().getBoundsInParent().intersects(checking.getBounds().getBoundsInParent())){
                checking.setTranslateX(checking.getTranslateX() - value);
                checking.getBounds().setTranslateX(checking.getTranslateX() + checking.getWidth() / 4);
                return flobject;
            }}

        return null;
    }
    public static Flobject checkTranslateY(Flobject checking, ArrayList<Flobject> checkers, double value){
        for (Flobject flobject:checkers){
            if(flobject != checking && flobject.getBounds().getBoundsInParent().intersects(checking.getBounds().getBoundsInParent())){
                checking.setTranslateY(checking.getTranslateY() - value);
                checking.getBounds().setTranslateY(checking.getTranslateY() + checking.getHeight() / 2);
              return flobject;
            }
        }
        return null;
    }
}
