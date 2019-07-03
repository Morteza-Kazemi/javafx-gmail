package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import model.messaging.Message;
import model.messaging.MessageType;
import model.user.User;
import model.user.UserAccount;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;


public class UserSignUpExtras {
    @FXML public Button signUp_button;
    @FXML public Button choose_button;
    @FXML public TextField phone_textField;
    @FXML public TextField gender_textField;


    private File photoFile = new File("resources/undefined.png");
    //akse maskhsare he;
    public void choosePhoto_button(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("choose your profile photo");
         photoFile = fileChooser.showOpenDialog(null);
        if(photoFile == null){
            new Alert(Alert.AlertType.ERROR).showAndWait();
        }
    }

    public void signUp_button(ActionEvent actionEvent) throws IOException {
//++++++++++++ ye online user negah dar ke be ezaye har user yedunast(vase har thread yebar new mishe faghat havaset be thread pooledt bashe)
        User connectedUser = Connection.getConnectedUser();
        UserAccount workingAccount = connectedUser.getAccount();
        workingAccount.setGender(gender_textField.getText());
        workingAccount.setPhoneNumber(phone_textField.getText());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //*****
        workingAccount.setProfilePhotoBArr(Files.readAllBytes(photoFile.toPath()));
        ObjectOutputStream oosToServer = Connection.getOos();
        try {
            System.out.println(connectedUser.getAccount().getProfilePhotoBArr().length);
            System.out.println(connectedUser.getAccount().getPhoneNumber());
            oosToServer.writeObject(new Message(MessageType.SAVE_USER, connectedUser));
            oosToServer.flush();
            oosToServer.flush();
            new PageLoader().load("User_login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
