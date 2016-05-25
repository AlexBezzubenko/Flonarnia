package Flonarnia.Scenes;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Hashtable;

/**
 * Created by Alexander on 24.03.2016.
 */
public class Authorization {
    static final int APP_W = 800;
    static final int APP_H = 600;
    private AnimationTimer animationTimer;
    private Stage primaryStage;
    private String login;
    private String parentPath;

    public Authorization(Stage primaryStage){
        URL url = getClass().getProtectionDomain().getCodeSource().getLocation(); //Gets the path
        String jarPath = null;
        try {
            jarPath = URLDecoder.decode(url.getFile(), "UTF-8"); //Should fix it to be read correctly by the system
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        parentPath = new File(jarPath).getParentFile().getPath(); //Path of the jar
        parentPath = parentPath + File.separator;
        this.primaryStage = primaryStage;
    }

    public Parent createContent(){
        Pane root = new Pane();
        root.setPrefSize(APP_W, APP_H);
        Image backgroundImg = new Image(getClass().getResourceAsStream("/Flonarnia/tools/res/logbackim.png"));
        ImageView background = new ImageView(backgroundImg);
        Image backgroundRectImage = new Image(getClass().getResourceAsStream("/Flonarnia/tools/res/logbackrect.png"));
        ImageView backgroundRect = new ImageView(backgroundRectImage);

        background.setFitWidth(APP_W);
        background.setFitHeight(APP_H);

        VBox vBox = new VBox();
        vBox.setSpacing(15);

        Text text = new Text();
        text.setVisible(false);

        TextField loginField = new TextField();
        loginField.setPromptText("login");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("password");

        Button loginButton = new Button("Sign in");
        Button registrationButton = new Button("Sign up");

        HBox hBox = new HBox();
        hBox.setSpacing(40);
        vBox.getChildren().addAll(loginField, passwordField, hBox, text);
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
                    Scene scene = new Scene(loader.createContent(login));
                    primaryStage.setScene(scene);
                    primaryStage.show();
                    loader.runLoaderTask(primaryStage);
                    this.stop();
                }
            }
        };

        loginButton.setOnMouseClicked(event -> {
            File file = new File(parentPath + "\\Base");
            if (!file.exists()) {
                if (file.mkdir()) {
                    System.out.println("Directory is created!");
                } else {
                    System.out.println("Failed to create directory!");
                }
            }

            Hashtable<String, String> base = new Hashtable<>();
            try (DataInputStream iStream =
                         new DataInputStream(new BufferedInputStream
                                 (new FileInputStream(parentPath + "\\Base\\" + "base.bin")))) {
                while (true) {
                    if (iStream.available() == 0) {
                        iStream.close();
                        break;
                    }
                    String login = iStream.readUTF();
                    String password = iStream.readUTF();
                    base.put(login, password);
                }
            } catch (FileNotFoundException e) {
                System.out.println("can't read file not found");
            } catch (IOException e1) {
                System.out.println("can't read io");
            }

            String login = loginField.getText();
            String password = passwordField.getText();
            for (String key: base.keySet()){
                if(login.equals(new String(key)) && password.equals(new String(base.get(key)))){
                    this.login = login;
                    animationTimer.start();
                    text.setText("OK");
                    text.setFill(Color.GREEN);
                    text.setVisible(true);
                    return;
                }
            }
            text.setText("Check input");
            text.setFill(Color.RED);
            text.setVisible(true);
        });

        Registration registration = new Registration(parentPath, loginField);
        registration.createContent();
        registration.setVisible(false);
        registrationButton.setOnMouseClicked(event -> {
            registration.clearContent();
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
    private TextField loginFieldA = new TextField();

    private PasswordField passwordField = new PasswordField();
    private PasswordField checkPasswordField = new PasswordField();
    private Button signUp = new Button("Sign Up");
    private Button cancel = new Button("Cancel");
    private Text text = new Text("Registered");
    private String parentPath;

    public Registration(String parentPath, TextField loginField){
        loginFieldA = loginField;
        this.parentPath = parentPath;
    }

    public void clearContent(){
        loginField.setText("");
        passwordField.setText("");
        checkPasswordField.setText("");
        text.setVisible(false);
    }

    public void createContent(){
        this.setTranslateX(Authorization.APP_W / 2 - 50);
        this.setTranslateY(Authorization.APP_H / 2 - 100);
        loginField.setPromptText("login");
        passwordField.setPromptText("password");
        checkPasswordField.setPromptText("confirm password");
        text.setVisible(false);
        HBox hbox = new HBox(signUp, cancel);
        hbox.setSpacing(40);
        vBox.getChildren().addAll(loginField, passwordField, checkPasswordField, hbox, text);
        vBox.setSpacing(15);
        this.getChildren().addAll(vBox);

        cancel.setOnMouseClicked(event->{
            this.setVisible(false);
        });

        signUp.setOnMouseClicked(event->{
            String login = loginField.getText();
            String password = passwordField.getText();
            if (login.length() >= 5 && password.length() >= 5 && password.contentEquals(checkPasswordField.getText())) {
                Hashtable<String, String> base = new Hashtable<>();
                try (DataInputStream iStream =
                             new DataInputStream(new BufferedInputStream
                                     (new FileInputStream(parentPath + "\\Base\\" + "base.bin")))) {
                    while (true) {
                        if (iStream.available() == 0) {
                            iStream.close();
                            break;
                        }
                        String log = iStream.readUTF();
                        String pass = iStream.readUTF();
                        base.put(log, pass);
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("can't read file not found");
                } catch (IOException e1) {
                    System.out.println("can't read io");
                }

                if (base.get(login) == null) {
                    try (DataOutputStream oStream = new DataOutputStream(
                            new BufferedOutputStream(new FileOutputStream(parentPath + "\\Base\\" + "base.bin", true)))) {
                        oStream.writeUTF(login);
                        oStream.writeUTF(password);
                        oStream.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("can't write file not found");
                    } catch (IOException e1) {
                        System.out.println("can't write io");
                    }
                    loginFieldA.setText(login);
                    text.setText("Registered");
                    text.setFill(Color.GREEN);
                    new Timeline(new KeyFrame(
                            Duration.millis(500),
                            e -> this.setVisible(false) )).play();
                }
                else{
                    text.setText("User already exists");
                    text.setFill(Color.RED);
                }
            }
            else{
                text.setText("Incorrect input");
                text.setFill(Color.RED);
            }
            text.setVisible(true);
        });
    }
}



