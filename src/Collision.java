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
            }
        }
        return false;
    }
    public static boolean checkTranslteY(Flobject checking, ArrayList<Flobject> checkers, double value){
        for (Flobject flobject:checkers){
            if(flobject != checking && flobject.rect.getBoundsInParent().intersects(checking.rect.getBoundsInParent())){
                checking.setTranslateY(checking.getTranslateY() - value);
                checking.rect.setTranslateY(checking.getTranslateY() + checking.HEIGHT / 2);
                if(checking.getTranslateY() < flobject.getTranslateY() + 100)
                    checking.toBack();
                else checking.toFront();

                return true;
            }
        }
        return false;
    }
}
