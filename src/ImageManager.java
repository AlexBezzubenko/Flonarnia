/**
 * Created by Alexander on 10.03.2016.
 */
import javafx.scene.image.Image;

import java.util.Hashtable;

public class ImageManager {

    private Hashtable<String,Image> images = new Hashtable<>();
    private Hashtable<String,double[]> NPCimageParams = new Hashtable<>();
    private Hashtable<String,double[]> EnemyImageParams = new Hashtable<>();
    static protected ImageManager instance;

    public static ImageManager getInstance(){
        if (instance == null){
            instance = new ImageManager();
        }
        return instance;
    }
    protected ImageManager(){
        images.put("PLAYER",new Image(getClass().getResourceAsStream("res/PLAYER.png")));
        images.put("TREE", new Image(getClass().getResourceAsStream("res/tree_0.png")));
        images.put("HOUSE", new Image(getClass().getResourceAsStream("res/house_type1.gif")));
        images.put("NPC", new Image(getClass().getResourceAsStream("res/NPC_1.png")));

        images.put("BUFFALO",new Image(getClass().getResourceAsStream("res/buffalo.png")));
        images.put("DARK_SOUL",new Image(getClass().getResourceAsStream("res/dark_soul.png")));
        images.put("DARK_SOUL2",new Image(getClass().getResourceAsStream("res/dark_soul2.png")));
        images.put("DRAGON", new Image(getClass().getResourceAsStream("res/dragon.png")));
        images.put("OGRE", new Image(getClass().getResourceAsStream("res/ogre.png")));
        images.put("UNDEAD", new Image(getClass().getResourceAsStream("res/undead.png")));

        EnemyImageParams.put("buffalo", new double[]{0, 0, 96, 96, 3});
        EnemyImageParams.put("dark_soul", new double[]{0, 0, 138, 131, 4});
        EnemyImageParams.put("dark_soul2", new double[]{0, 0, 101, 96, 4});
        EnemyImageParams.put("dragon", new double[]{0, 0, 96, 96, 4});
        EnemyImageParams.put("undead", new double[]{0, 0, 80, 96, 3});
        EnemyImageParams.put("ogre", new double[]{0, 0, 107, 144, 4});

        NPCimageParams.put("player", new double[]{0, 0, 32, 60});
        NPCimageParams.put("guider", new double[]{0, 0, 32, 60});
        NPCimageParams.put("warrior", new double[]{0, 268, 32, 60});
        NPCimageParams.put("shaman", new double[]{210, 278, 31, 52});
        NPCimageParams.put("trader", new double[]{212, 0, 32, 60});
        NPCimageParams.put("blacksmith", new double[]{624, 0, 32, 63});
        NPCimageParams.put("gatekeeper", new double[]{421, 0, 32, 60});
    }

    public Image getImage(String key){
        return images.get(key);
    }
    public double[] getNPCImageParams(String key){
        return NPCimageParams.get(key);
    }
    public double[] getEnemyImageParams(String key){
        return EnemyImageParams.get(key);
    }
}
