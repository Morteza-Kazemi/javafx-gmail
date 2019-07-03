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
import model.user.UserAccount;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


public class compose {
    @FXML
    TextField adress_textfield;
    @FXML
    TextField subject_textfield;
    @FXML
    TextArea textArea;

    private File attachedFile;

    public void send(ActionEvent actionEvent) throws IOException {
        User sender = Connection.getConnectedUser();
        Message message = new Message(

                sender
                , new User(adress_textfield.getText())
                , textArea.getText()
                , LocalDate.now()
                , subject_textfield.getText()
                //+++++ this should be changed...
                , new byte[1]
                , MessageType.MAIL

        );
        ObjectOutputStream oos = Connection.getOos();
        oos.writeObject(message);
        oos.flush();
        addToConversations(sender, message);//so that the sender can see the sent message immediately.
    }

    //*****
    //(written javadoc and put links)
    /**
     * the sender can see the sent message immediately on the graphical interface
     *
     * @param sender {@link User}
     * @param message {@link Message}
     */
    private void addToConversations(User sender, Message message) {
        Conversation newConv = new Conversation();
        newConv.addMessage(message);
        List<Conversation> conversationList = sender.getConversations();
        conversationList.add(newConv);
        sender.setConversations(conversationList);
    }

    public void attach(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("choose your attachment");
        attachedFile = fileChooser.showOpenDialog(null);
    }
}

