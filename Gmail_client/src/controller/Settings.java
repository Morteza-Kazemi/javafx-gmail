package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.user.User;
import model.user.UserAccount;

import java.io.IOException;

public class Settings {
    @FXML TextField name_textField;
    @FXML TextField lastName_textField;
    @FXML TextField phone_textField;
    @FXML TextField gender_textField;
    @FXML PasswordField passwordField;
    //+++++ when the user exits the application you should change the user saved in the server list.

    UserAccount connectedAccount = Connection.getConnectedUser().getAccount();
    public void choosePhoto(ActionEvent actionEvent) {

    }

    public void setInformation(ActionEvent actionEvent) {
        String newName = name_textField.getText();
        String newLastName = lastName_textField.getText();
        String newGender = gender_textField.getText();
        String newPhone = phone_textField.getText();
        String newPassword = passwordField.getText();
        if(!newGender.isEmpty()){ connectedAccount.setGender(newGender);}
        if(!newLastName.isEmpty()){ connectedAccount.setLastName(newGender);}
        if(!newName.isEmpty()){ connectedAccount.setName(newGender);}
        if(!newPhone.isEmpty()){ connectedAccount.setPhoneNumber(newGender);}
        if(!newPassword.isEmpty()){
            if(UserSignUp.passwordIsStrong(newPassword)){
                connectedAccount.setPassword(newPassword);
            }
            else{
                new Alert(Alert.AlertType.ERROR,"password is not strong enough").showAndWait();
            }
        }
        //++++ and one for the photo to be set.
        new Alert(Alert.AlertType.CONFIRMATION,"changes applied (non-empty fields changed)").showAndWait();
    }

    public void back(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("homePage");
    }
}
