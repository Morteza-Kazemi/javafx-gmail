package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Connection;
import model.PageLoader;

import java.io.IOException;
import java.net.Socket;


public class serverIPSetter {
    public static Socket clientSocket = null;
    @FXML
    public TextField serverIPSetter_textField;
    @FXML
    public Button connect_button;
    @FXML
    public TextField port_textField;

    public void setIP(ActionEvent actionEvent) {
            try {
                clientSocket = new Socket(serverIPSetter_textField.getText(),Integer.parseInt(port_textField.getText()));
                new PageLoader().load("User_login");
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
