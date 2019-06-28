package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Connection;


public class serverIPSetter {
    @FXML
    public TextField serverIPSetter_textField;
    @FXML
    public Button connect_button;

    public void setIP(ActionEvent actionEvent) {
        new Connection().setServerIP(serverIPSetter_textField.getText());

    }
}
