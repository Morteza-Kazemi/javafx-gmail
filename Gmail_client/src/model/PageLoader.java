package model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class PageLoader {
    private static Stage stage;

    public static void initStage(Stage primaryStage) {
        stage = primaryStage;
        stage.initStyle(StageStyle.DECORATED);
    }

    public void load(String url) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/" + url + ".fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("../view/User_login.fxml"));
        stage.setScene(new Scene(root, 1000, 650));
        stage.show();
    }
}
