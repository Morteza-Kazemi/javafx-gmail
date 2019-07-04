package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import model.messaging.Conversation;
import model.messaging.Message;
import model.messaging.MessageType;
import model.user.User;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.List;


public class compose {
    @FXML
    TextField adress_textfield;
    @FXML
    TextField subject_textfield;
    @FXML
    TextArea textArea;

    private File attachedFile;
    ObjectOutputStream oos = Connection.getOos();

    public void send(ActionEvent actionEvent) throws IOException {
        User sender = Connection.getConnectedUser();
        Message message = new Message(

                sender
                , new User(adress_textfield.getText())
                , textArea.getText()
                , LocalDateTime.now()
                , subject_textfield.getText()
                //+++++ this should be changed...
                , new byte[1]
                , MessageType.MAIL

        );
        oos.writeObject(message);
        System.out.println("send request sent to server...");
        oos.flush();
        addToConversations(sender, message);//so that the sender can see the sent message immediately.
        new Alert(Alert.AlertType.CONFIRMATION,"message sent");
        new PageLoader().load("homePage");
    }

    //*****
    //(written javadoc and put links)
    /**
     * the sender can see the sent message immediately on the graphical interface
     *
     * @param sender {@link User}
     * @param message {@link Message}
     */
    private void addToConversations(User sender, Message message) throws IOException {
        Conversation newConv = new Conversation();
        newConv.addMessage(message);
        sender.addConversation(newConv);
        oos.writeObject(new Message(MessageType.SAVE_USER,sender));
        System.out.printf("sender saved to server");
    }

    public void attach(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("choose your attachment");
        attachedFile = fileChooser.showOpenDialog(null);
    }
    public void back(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("homePage");
    }
}

