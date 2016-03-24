import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by Alexander on 24.03.2016.
 */
public class Authorization {
    private  static final int APP_W = 800;
    private static final int APP_H = 600;
    static long startNanoTime;
    private Stage primaryStage;

    public Authorization(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public Parent createContent(){
        Pane root = new Pane();
        root.setPrefSize(APP_W, APP_H);
        Image backgroundImg = new Image(getClass().getResourceAsStream("res/loginBackground.jpg"));
        ImageView background = new ImageView(backgroundImg);

        background.setFitWidth(APP_W);
        background.setFitHeight(APP_H);

        VBox vBox = new VBox();
        vBox.setSpacing(15);


        TextField loginField = new TextField();
        loginField.setPromptText("login");

        TextField passwordField = new TextField();
        passwordField.setPromptText("password");

        Button loginButton = new Button("Sign in");
        Button registrationButton = new Button("Sign up");



        HBox hBox = new HBox();
        hBox.setSpacing(40);
        vBox.getChildren().addAll(loginField, passwordField, hBox);
        hBox.getChildren().addAll(loginButton,registrationButton);

        BorderPane borderPane = new BorderPane(vBox);
        borderPane.setTranslateX(APP_W / 2 - 50);
        borderPane.setTranslateY(APP_H / 2 - 100);
        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setTranslateX(borderPane.getTranslateX());
        progressBar.setTranslateY(APP_H / 2 + 30);
        progressBar.setPrefWidth(150);
        progressBar.setVisible(false);


        AnimationTimer animationTimer = new AnimationTimer(){
            @Override
            public void handle(long now) {
                double t = (now - startNanoTime) / 1000000000.0;
                progressBar.setProgress(t / 2);
            }
        };

        progressBar.progressProperty().addListener(e->{
            if(progressBar.getProgress() > 0.99){
                animationTimer.stop();
                Loader loader = new Loader();
                Scene scene = new Scene(loader.createContent());
                primaryStage.setScene(scene);
                primaryStage.show();
                loader.runLoaderTask(primaryStage);
            }
        });

        loginButton.setOnMouseClicked(event -> {
            startNanoTime  = System.nanoTime();
            progressBar.setVisible(true);
            animationTimer.start();
        });
        root.getChildren().addAll(background, borderPane, progressBar);

        Platform.runLater(() -> loginButton.requestFocus());
        return root;
    }
}



