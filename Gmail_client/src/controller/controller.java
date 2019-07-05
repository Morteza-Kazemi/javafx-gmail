package controller;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import model.messaging.Message;
import model.messaging.MessageType;
import model.user.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;

public class controller extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        PageLoader.initStage(primaryStage);
        //+++++ this should change
        new PageLoader().load("setServerPort");
//        new PageLoader().load("User_signUp_extras");
        primaryStage.getIcons().add(new Image(Paths.get("resources\\Gmail.png").toUri().toString()));
    }
    //*****
    //complete working with files and saving the users and all information
    //useful javadoc
    //++++ could be better by a database having directories for each user
    /**
     * saves the connected user and the changes made to itself when finished working with the application.
     * @throws IOException happens when there is a problem saving the user through socket programming and file streams
     */
    @Override
    public void stop() throws IOException {
        ObjectOutputStream oos = Connection.getOos();
        oos.writeObject(new Message(MessageType.SAVE_USER,Connection.getConnectedUser()));
    }



}
