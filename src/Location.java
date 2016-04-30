import Panels.TargetPanel;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by Alexander on 24.04.2016.
 */
public class Location {
    private Stage primaryStage;
    private Player player;
    private final double APP_W;
    private final double APP_H;

    public static Pane appRoot = new Pane();
    public static Pane gameRoot = new Pane();
    public static Pane backgroundRoot = new Pane();
    public static Pane foregroundRoot = new Pane();
    public static TargetPanel targetPanel;
    public static ArrayList<Flobject> flobjects = new ArrayList<>();

    NPC guider = new NPC(1950, 500, "guider");
    NPC warrior = new NPC(2150, 500, "warrior");
    NPC shaman = new NPC(2350, 500, "shaman");
    NPC trader = new NPC(2550, 500, "trader");
    NPC gatekeeper = new NPC(1100, 900, "gatekeeper");
    NPC blacksmith = new NPC(1100, 1000, "blacksmith");
    Enemy buffalo = new Enemy(1700, 700, "buffalo");
    Enemy dark_soul = new Enemy(1100, 700, "dark_soul2");
    Enemy dragon = new Enemy(1200, 700, "dragon");
    Enemy ogre = new Enemy(1400, 700, "ogre");
    Enemy undead = new Enemy(1300, 700, "undead");

    Flobject[] houses = new Flobject[]{
            new TypedHouse(1900, 200, "house_type_1"),
            new TypedHouse(2100, 200, "house_type_2"),
            new TypedHouse(2300, 200, "house_type_3"),
            new TypedHouse(2500, 200, "house_type_4")
    };



    ArrayList<ImageView> gv = new ArrayList<>();


    Location(Stage primaryStage, Player player){
        this.primaryStage = primaryStage;
        this.player = player;
        APP_H = primaryStage.getHeight();
        APP_W = primaryStage.getWidth();
        double x = APP_W / 4 + 600 + 1000;
        double y = APP_H / 4 + 600;
        //player = new Player(x, y);
        gameRoot.setLayoutX( -(x - APP_W / 2));
        gameRoot.setLayoutY( -(y - APP_H / 2));

        for (Flobject f: houses){
            flobjects.add(f);
        }
        flobjects.add(player);
        flobjects.add(new Tree(500, 400));
        flobjects.add(new Tree(500, 600));
        flobjects.add(new Tree(800, 400));
        flobjects.add(new Tree(1000, 500));
        flobjects.add(new House(-100, -100));
        flobjects.add(trader);
        flobjects.add(guider);
        flobjects.add(warrior);
        flobjects.add(shaman);
        flobjects.add(gatekeeper);
        flobjects.add(blacksmith);
        flobjects.add(dragon);
        flobjects.add(dark_soul);
        flobjects.add(buffalo);
        flobjects.add(ogre);
        flobjects.add(undead);
    }

    public Scene CreateLocation(){
        Image grassImage = new Image(getClass().getResourceAsStream("/res/map.png"));
        ImageView grassView = new ImageView(grassImage);
        grassView.setViewport(new Rectangle2D(0, 0, APP_W, APP_H));


        double gvTranslateX = -APP_W;
        double gvTranslateY = -APP_H * 2;
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0){
                gvTranslateX = -APP_W;
                gvTranslateY += APP_H;
            }
            ImageView gvItem = new ImageView(grassImage);
            gvItem.setTranslateX(gvTranslateX);
            gvItem.setTranslateY(gvTranslateY);
            gv.add(gvItem);
            gvTranslateX += APP_W;
        }

        player.translateXProperty().addListener((obs,old,newValue)->{
            int offset = newValue.intValue();
            if(offset > APP_W / 2 || offset <= APP_W / 2 /*&& offset < APP_W * 5 - 640*/){
                gameRoot.setLayoutX( -(offset - APP_W / 2));
            }
        });
        player.translateYProperty().addListener((obs,old,newValue)->{
            int offset = newValue.intValue();
            if(offset > APP_H / 2 || offset <= APP_H / 2 /*&& offset < APP_W * 5 - 640*/){
                gameRoot.setLayoutY( -(offset - APP_H / 2));
                //grassView.setLayoutY( -(offset - APP_H /2 ));
            }
        });



        gameRoot.getChildren().addAll(gv);

        //gameRoot.getChildren().addAll( player);
        backgroundRoot.getChildren().addAll(gv);//reislor
        foregroundRoot.getChildren().addAll(flobjects);
        targetPanel = new TargetPanel(APP_W / 2, 0);
        gameRoot.getChildren().addAll(backgroundRoot,foregroundRoot);
        appRoot.getChildren().addAll(gameRoot, targetPanel);

        Scene scene = new Scene(appRoot, APP_W, APP_W);
        primaryStage.setScene(scene);
        return scene;
    }
}
