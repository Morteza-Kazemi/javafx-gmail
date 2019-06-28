package controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.PageLoader;

import java.io.IOException;

public class controller extends Application {



    public static void main(String[] args) {

        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        PageLoader.initStage(primaryStage);
        new PageLoader().load("setServerPort");
    }



}
