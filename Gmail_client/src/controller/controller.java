package controller;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import model.user.User;

import java.io.IOException;
import java.nio.file.Paths;

public class controller extends Application {

    public static User workingUser;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        PageLoader.initStage(primaryStage);
        //+++++ this should change
//        new PageLoader().load("setServerPort");
        new PageLoader().load("HomePage");
        primaryStage.getIcons().add(new Image(Paths.get("resources\\Gmail.png").toUri().toString()));

    }



}
