package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.messaging.Message;
import model.messaging.MessageType;
import model.user.User;
import model.user.UserAccount;


import java.io.*;

public class logIn {

    @FXML public Button signUp_button;
    @FXML public Button logIn_button;
    @FXML public TextField username_textField;
    @FXML public TextField password_passwordField;


    public void logIn(ActionEvent actionEvent) {
        try {
//            serverIPSetter.clientSocket = new Socket("localhost",8888);
            ObjectOutputStream oos = Connection.getOos();
            ObjectInputStream ois = Connection.getOis();
            UserAccount account = new UserAccount(username_textField.getText(),password_passwordField.getText());
            //send the info to the server inorder to check if the username and password are valid.
            oos.writeObject(new Message(MessageType.SIGN_IN,account));
            oos.flush();
            Message respond = (Message) ois.readObject();
//            System.out.println(respond.getMessageType());
            if(respond.getMessageType().equals(MessageType.ACCEPTED)){
                System.out.println("login accepted");
                Connection.setConnectedUser((User) respond.getObject());
//                System.out.println("profile photo file : "+Connection.getConnectedUser().getAccount().getProfilePhotoBArr().length);
                new PageLoader().load("homePage");
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR,"wrong password or username");
                alert.showAndWait();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void signUp(ActionEvent actionEvent) {
        try {
            new PageLoader().load("User_signUp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
