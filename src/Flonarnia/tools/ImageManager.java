package Flonarnia.tools; /**
 * Created by Alexander on 10.03.2016.
 */
import javafx.scene.image.Image;

import java.util.Hashtable;

public class ImageManager {

    private Hashtable<String,Image> images = new Hashtable<>();
    private Hashtable<String,Image> icons = new Hashtable<>();
    private Hashtable<String,double[]> NPCImageParams = new Hashtable<>();
    private Hashtable<String,double[]> EnemyImageParams = new Hashtable<>();
    private Hashtable<String,double[]> FlobjectsImageParams = new Hashtable<>();
    private Hashtable<String,double[]> HousesImageParams = new Hashtable<>();
    private Hashtable<String,double[]> IconParams = new Hashtable<>();

    private Hashtable<String, Hashtable<String, double[]>> params = new Hashtable<>();

    static protected ImageManager instance;

    public static ImageManager getInstance(){
        if (instance == null){
            instance = new ImageManager();
        }
        return instance;
    }
    protected ImageManager(){
        images.put("PLAYER",new Image(getClass().getResourceAsStream("res/PLAYER.png")));
        images.put("TREE", new Image(getClass().getResourceAsStream("res/tree.png")));
        images.put("MOUNTAINH", new Image(getClass().getResourceAsStream("res/horizontal_mountain.png")));
        images.put("MOUNTAINV", new Image(getClass().getResourceAsStream("res/vertical_mountain.png")));

        images.put("PORTAL", new Image(getClass().getResourceAsStream("res/portal.png")));
        images.put("FIRE", new Image(getClass().getResourceAsStream("res/fire.png")));
        images.put("HOUSE", new Image(getClass().getResourceAsStream("res/house_type1.gif")));
        images.put("TYPEDHOUSE", new Image(getClass().getResourceAsStream("res/house.png")));
        images.put("NPC", new Image(getClass().getResourceAsStream("res/NPC.png")));


        images.put("BUFFALO",new Image(getClass().getResourceAsStream("res/buffalo.png")));
        images.put("DARK SOUL",new Image(getClass().getResourceAsStream("res/dark_soul.png")));
        images.put("DRAGON", new Image(getClass().getResourceAsStream("res/dragon.png")));
        images.put("OGRE", new Image(getClass().getResourceAsStream("res/ogre.png")));
        images.put("UNDEAD", new Image(getClass().getResourceAsStream("res/undead.png")));

        icons.put("poison",  new Image(getClass().getResourceAsStream("res/poisons.png")));
        icons.put("item",  new Image(getClass().getResourceAsStream("res/items.png")));
        icons.put("weapon",  new Image(getClass().getResourceAsStream("res/weapons.png")));


        EnemyImageParams.put("Buffalo", new double[]{0, 0, 96, 96, 3});
        EnemyImageParams.put("Dark Soul", new double[]{0, 0, 101, 96, 4});
        EnemyImageParams.put("Dragon", new double[]{0, 0, 96, 96, 4});
        EnemyImageParams.put("Undead", new double[]{0, 0, 80, 96, 3});
        EnemyImageParams.put("Ogre", new double[]{0, 0, 107, 144, 4});

        NPCImageParams.put("player", new double[]{0, 0, 32, 60, 6});
        NPCImageParams.put("Guider", new double[]{0, 0, 32, 60, 6});
        NPCImageParams.put("Warrior", new double[]{0, 268, 32, 60, 6});
        NPCImageParams.put("Shaman", new double[]{210, 278, 31, 52, 6});
        NPCImageParams.put("Trader", new double[]{212, 0, 32, 60, 6});
        NPCImageParams.put("Blacksmith", new double[]{624, 0, 32, 63, 6});
        NPCImageParams.put("GateKeeper", new double[]{421, 0, 32, 60, 6});

        double houseScale = 1.5;
        HousesImageParams.put("house_type_1", new double[]{519,12,100 * houseScale, 220 * houseScale, 1});
        HousesImageParams.put("house_type_2", new double[]{632,12,100 * houseScale, 220 * houseScale, 1});
        HousesImageParams.put("house_type_3", new double[]{744,12,100 * houseScale, 220 * houseScale, 1});
        HousesImageParams.put("house_type_4", new double[]{857,12,100 * houseScale, 220 * houseScale, 1});

        FlobjectsImageParams.put("tree", new double[]{0, 0, 150, 200, 1});
        FlobjectsImageParams.put("mountainH", new double[]{0, 0, 1020, 242, 1});
        FlobjectsImageParams.put("mountainV", new double[]{0, 0, 268, 809, 1});
        FlobjectsImageParams.put("portal", new double[]{-3, 3, 128, 65, 5});
        FlobjectsImageParams.put("house", new double[]{0, 0, 225, 300, 1});
        FlobjectsImageParams.put("fire", new double[]{-2, 0, 64, 68, 8});

        IconParams.put("mana", new double[]{180, 0, 60, 60});
        IconParams.put("scroll", new double[]{0, 0, 60, 60});
        IconParams.put("attack", new double[]{60, 0, 60, 60});
        IconParams.put("health", new double[]{120, 120, 60, 60});
        IconParams.put("endurance", new double[]{120, 60, 60, 60});


        IconParams.put("Infinity Sharper",  new double[]{0,   0, 60, 60});
        IconParams.put("Forgotten Sword",      new double[]{60,   0, 60, 60});
        IconParams.put("Infinity Cutter",   new double[]{120, 0, 60, 60});
        IconParams.put("Saint Spear", new double[]{180, 0, 60, 60});
        IconParams.put("Sword of Life", new double[]{0, 60, 60, 60});
        IconParams.put("Elemental Sword", new double[]{60, 60, 60, 60});
        IconParams.put("Dragon Slayer", new double[]{120, 60, 60, 60});
        IconParams.put("Sword of Miracles", new double[]{180, 60, 60, 60});

        params.put("NPC", NPCImageParams);
        params.put("Enemy", EnemyImageParams);
        params.put("Flobjects", FlobjectsImageParams);
        params.put("Houses", HousesImageParams);
        params.put("Icons", IconParams);
    }

    public Image getImage(String key){
        return images.get(key);
    }
    public Image getIcon(String key){
        return icons.get(key);
    }

    public double[] getParams(String paramName, String objectName){
        return params.get(paramName).get(objectName);
    }
}
