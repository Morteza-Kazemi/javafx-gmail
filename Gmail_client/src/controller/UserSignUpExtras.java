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

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;


public class UserSignUpExtras {
    @FXML public Button signUp_button;
    @FXML public Button choose_button;
    @FXML public TextField phone_textField;
    @FXML public TextField gender_textField;


    private File photoFile = null;
    //akse maskhsare he;
    public void choosePhoto_button(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("choose your profile photo");
         photoFile = fileChooser.showOpenDialog(null);
        if(photoFile == null){
            new Alert(Alert.AlertType.ERROR).showAndWait();
        }
        System.out.println("photo was chosen");
    }

    public void signUp_button(ActionEvent actionEvent) throws IOException, InterruptedException {
        ObjectOutputStream oosToServer = Connection.getOos();
        User connectedUser = Connection.getConnectedUser();
        UserAccount workingAccount = connectedUser.getAccount();
        workingAccount.setGender(gender_textField.getText());
        System.out.println(gender_textField.getText()+"gender");
        workingAccount.setPhoneNumber(phone_textField.getText());
        System.out.println("phone "+ phone_textField.getText());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //*****
        if(photoFile!=null) {
            connectedUser.setProfilePhotoBArr(Files.readAllBytes(photoFile.toPath()));
        }
        //*****
        else{
            System.out.println("photo was null");
            connectedUser.setProfilePhotoBArr(Files.readAllBytes(Paths.get("../resources/undefined.png")));
        }
        try {
            Connection.setConnectedUser(connectedUser);
            System.out.println("last bullet "+Connection.getConnectedUser().getAccount().getProfilePhotoBArr().length);
            System.out.println(connectedUser.getAccount().getPhoneNumber());
            Thread.sleep(1000);
            oosToServer.writeObject(new Message(MessageType.SAVE_EXTRAS,connectedUser.getAccount().getUserName(),
                    connectedUser.getAccount().getPhoneNumber() , connectedUser.getAccount().getGender()
                    ,connectedUser.getAccount().getProfilePhotoBArr()));
            oosToServer.flush();
            Thread.sleep(1000);
            new PageLoader().load("User_login");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
