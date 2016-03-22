/**
 * Created by Alexander on 10.03.2016.
 */
import javafx.scene.image.Image;

import java.util.Hashtable;

public class ImageManager {

    private Hashtable<String,Image> images = new Hashtable<>();
    static protected ImageManager instance;
    public static ImageManager getInstance(){
        if (instance == null){
            instance = new ImageManager();
        }
        return instance;
    }
    protected ImageManager(){
        images.put("PLAYER",new Image(getClass().getResourceAsStream("res/0.png")));
    }

    public Image getImage(String key){
        return images.get(key);
    }
}
