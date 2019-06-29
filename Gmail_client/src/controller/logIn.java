package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.PageLoader;
import model.messaging.Message;
import model.messaging.MessageType;
import model.user.UserAccount;

import java.io.*;

public class logIn {

    @FXML public Button signUp_button;
    @FXML public Button logIn_button;
    @FXML public TextField username_textField;
    @FXML public TextField password_passwordField;


    public void logIn(ActionEvent actionEvent) {
        try {
            OutputStream ostream = serverIPSetter.clientSocket.getOutputStream();
            ObjectOutputStream objostream = new ObjectOutputStream(ostream);
            InputStream istream = serverIPSetter.clientSocket.getInputStream();
            ObjectInputStream objistream = new ObjectInputStream(istream);
            UserAccount account = new UserAccount(username_textField.getText(),password_passwordField.getText());
            Message respond = (Message) objistream.readObject();
            if(respond.getMessageType().equals(MessageType.ACCEPTED)){
                new PageLoader().load("homePage");
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR,"wrong password");
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
