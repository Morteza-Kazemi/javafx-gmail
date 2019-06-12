package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.messaging.Message;
import model.messaging.MessageType;

import java.io.*;
import java.net.Socket;

public class UserSignUp {
    @FXML public Button create_button;
    @FXML public TextField name_textField;
    @FXML public TextField lastName_textField;
    @FXML public TextField username_textField;
    @FXML private PasswordField password_passwordField;
    @FXML public DatePicker birthDay_datePicker;
    @FXML public Label invalid_label;

    Socket connectionToServerSckt = null;
    ObjectOutputStream outputStreamToServer = null;
    ObjectInputStream inputStreamFromServer = null;


    private void validate() throws IOException, ClassNotFoundException {
        boolean validInfo = true;
        String password = password_passwordField.getText();
        if(!passwordIsStrong(password)){//password strength can be evaluated here so no need to send it to the server.
            invalid_label.setText("password not strong enough");
            invalid_label.setVisible(true);
        }
        else{
            Message signUpMsg = new Message(MessageType.SIGN_UP,username_textField.getText());
            outputStreamToServer.writeObject(signUpMsg);
            Message answer = (Message) inputStreamFromServer.readObject();
            //++++++++  wouldn't there be a conflict then ? maybe the server had accepted something else...?
            if(answer.getMessageType().equals(MessageType.REJECTED)){
                invalid_label.setText(answer.getText());
                invalid_label.setVisible(true);
            }
            else{
                //load the main page:
                //TODO
            }
        }
    }
    private boolean passwordIsStrong(String password){
        if(password.length()<8){
            return false;
        }
        Boolean containsDigit = false,containsUpperCase = false,containsLowerCase = false;
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if(!containsDigit && '0'<=ch && ch<='9'){
                containsDigit = true;
            }
            else if(!containsUpperCase && 'A'<=ch && ch<='Z'){
                containsUpperCase = true;
            }
            else if (!containsLowerCase && 'a'<=ch && ch<='z'){
                containsLowerCase = true;
            }
        }
        return containsDigit && containsLowerCase && containsUpperCase;
    }
}

