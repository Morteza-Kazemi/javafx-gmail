package controller.messagesInAConv;

import controller.PageLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.messaging.Message;

import java.io.IOException;
import java.time.LocalDateTime;

public class MessagesListItemController {
    private Message message;
    @FXML
    private AnchorPane root;
    @FXML
    private Label sender_label;
    @FXML private Label text_label;


    public MessagesListItemController(Message message) {
        this.message = message;
        try {
            new PageLoader().load("message_listview",this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    LocalDateTime localDateTime;

    public AnchorPane init(){
        text_label.setText(message.getText());
        sender_label.setText(message.getSender().getAddress());
        return root;
    }
}
