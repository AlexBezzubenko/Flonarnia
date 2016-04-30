import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;

/**
 * Created by Alexander on 24.03.2016.
 */
public class Authorization {
    static final int APP_W = 800;
    static final int APP_H = 600;
    private AnimationTimer animationTimer;
    private Stage primaryStage;

    public Authorization(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public Parent createContent(){
        Pane root = new Pane();
        root.setPrefSize(APP_W, APP_H);
        /* Image backgroundImg = new Image(getClass().getResourceAsStream("res/loginBackground.jpg")); */
        Image backgroundImg = new Image(getClass().getResourceAsStream("res/123.png"));
        ImageView background = new ImageView(backgroundImg);
        Image backgroundRectImage = new Image(getClass().getResourceAsStream("res/logbackrect.png"));
        ImageView backgroundRect = new ImageView(backgroundRectImage);


        background.setFitWidth(APP_W);
        background.setFitHeight(APP_H);

        VBox vBox = new VBox();
        vBox.setSpacing(15);


        TextField loginField = new TextField();
        loginField.setPromptText("login");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("password");

        Button loginButton = new Button("Sign in");
        Button registrationButton = new Button("Sign up");

        HBox hBox = new HBox();
        hBox.setSpacing(40);
        vBox.getChildren().addAll(loginField, passwordField, hBox);
        hBox.getChildren().addAll(loginButton,registrationButton);

        BorderPane borderPane = new BorderPane(vBox);
        borderPane.setTranslateX(APP_W - APP_W * 0.3);
        borderPane.setTranslateY(APP_H / 3 );


        backgroundRect.setTranslateX(-350);
        animationTimer = new AnimationTimer(){
            @Override
            public void handle(long now) {
                if (backgroundRect.getTranslateX() < 20) {
                    backgroundRect.setTranslateX(backgroundRect.getTranslateX() + 3);
                }
                else {

                    Loader loader = new Loader();
                    Scene scene = new Scene(loader.createContent());
                    primaryStage.setScene(scene);
                    primaryStage.show();
                    loader.runLoaderTask(primaryStage);
                    this.stop();
                }
            }
        };

        loginButton.setOnMouseClicked(event -> {
            animationTimer.start();
        });

        Registration registration = new Registration();
        registration.createContent();
        registration.setVisible(false);
        registrationButton.setOnMouseClicked(event -> {
            registration.setVisible(true);
        });
        root.getChildren().addAll(backgroundRect,background, borderPane, registration);

        Platform.runLater(() -> loginButton.requestFocus());
        return root;
    }
}

class Registration extends Pane{
    private VBox vBox = new VBox();
    private TextField loginField = new TextField();

    private PasswordField passwordField = new PasswordField();
    private PasswordField checkPasswordField = new PasswordField();
    private Button signUp = new Button("Sigh Up");
    private Button cancel = new Button("Cancel");

    public void createContent(){
        this.setTranslateX(Authorization.APP_W / 2 - 50);
        this.setTranslateY(Authorization.APP_H / 2 - 100);
        loginField.setPromptText("login");
        passwordField.setPromptText("password");
        checkPasswordField.setPromptText("confirm password");
        HBox hbox = new HBox(signUp, cancel);
        hbox.setSpacing(40);
        vBox.getChildren().addAll(loginField, passwordField, checkPasswordField, hbox);
        vBox.setSpacing(15);
        this.getChildren().addAll(vBox);

        cancel.setOnMouseClicked(event->{
            this.setVisible(false);
        });

        signUp.setOnMouseClicked(event->{
            String login = loginField.getText();
            String password = passwordField.getText();
                if (login.length() >= 5 && password.length() >= 5 && password.contentEquals(checkPasswordField.getText())) {
                    File file = new File("src/file.txt");
                    try {
                        if (!file.exists()) {
                            file.createNewFile();
                        }
                    } catch (IOException o) {
                    }
                    try (FileWriter writer = new FileWriter("src/file.txt", true)) { //append
                        String text = login + " " + password + "\n";
                        writer.write(text);
                        writer.close();
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
        });
    }
}



