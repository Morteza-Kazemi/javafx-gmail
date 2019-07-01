package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import model.user.UserAccount;

import java.io.IOException;

import static controller.controller.workingUser;

public class UserSignUpExtras {
    @FXML public Button signUp_button;
    @FXML public Button choose_button;
    @FXML public TextField phone_textField;
    @FXML public TextField gender_textField;

    FileChooser fileChooser = new FileChooser();


    public void choosePhoto_button(ActionEvent actionEvent) {
        fileChooser.showOpenDialog(null);

    }

    public void signUp_button(ActionEvent actionEvent) {
//++++++++++++ ye online user negah dar ke be ezaye har user yedunast(vase har thread yebar new mishe faghat havaset be thread pooledt bashe)
        UserAccount workingAccount = workingUser.getAccount();
        workingAccount.setGender(gender_textField.getText());
        workingAccount.setGender(phone_textField.getText());
        //++++++ assign the image here...
        try {
            new PageLoader().load("User_login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
