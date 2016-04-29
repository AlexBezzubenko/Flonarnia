import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Alexander on 14.03.2016.
 */
public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Authorization authorization = new Authorization(primaryStage);
       // Scene scene = new Scene(authorization.createContent());
       // primaryStage.setScene(scene);
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setTitle("Flonarnia");
        primaryStage.setResizable(false);
        Flonarnia game = new Flonarnia(primaryStage);
        game.run();
        /*File file = new File("src/file.txt");

        try (FileReader reader= new FileReader("src/file.txt")) {
            char [] a = new char[50];
            reader.read(a);
            System.out.println(a);
            reader.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }*/
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
