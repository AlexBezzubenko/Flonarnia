import javafx.scene.shape.Circle;

import java.util.ArrayList;

/**
 * Created by Alexander on 13.04.2016.
 */
public class Collision {
    public static boolean checkTranslteX(Flobject checking, ArrayList<Flobject> checkers, double value){
        for (Flobject flobject:checkers){
            if(flobject != checking && flobject.rect.getBoundsInParent().intersects(checking.rect.getBoundsInParent())){
                checking.setTranslateX(checking.getTranslateX() - value);
                checking.rect.setTranslateX(checking.getTranslateX() + checking.WIDTH / 4);
                return true;
            }}

        return false;
    }
    public static boolean checkTranslteY(Flobject checking, ArrayList<Flobject> checkers, double value){
        for (Flobject flobject:checkers){

            if(Math.abs(checking.getTranslateY()- flobject.getTranslateY())<200 ||Math.abs(checking.getTranslateX()- flobject.getTranslateX())<200){
            if(flobject != checking&&checking.getTranslateY() > flobject.getTranslateY()){
                    System.out.println("front");
                    System.out.println("player: "+checking.getTranslateY());
                    System.out.println("other: "+flobject.getTranslateY());
                    checking.toFront();


            if(flobject != checking&&checking.getTranslateY() < flobject.getTranslateY()+150){
                System.out.println("back");
                System.out.println("player: "+checking.getTranslateY());
                System.out.println("other: "+flobject.getTranslateY());
                checking.toBack();
            }}}
            if(flobject != checking && flobject.rect.getBoundsInParent().intersects(checking.rect.getBoundsInParent())){
                checking.setTranslateY(checking.getTranslateY() - value);
                checking.rect.setTranslateY(checking.getTranslateY() + checking.HEIGHT / 2);
              return true;
            }
        }
        return false;
    }
}
