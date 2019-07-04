package controller.messagesInAConv;

import controller.controller;
import controller.PageLoader;
import controller.messagesInAConv.MessagesListItem;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import model.messaging.Conversation;
import model.messaging.Message;
import model.messaging.MessageType;
import model.user.User;
//*****
import java.io.IOException;
import java.time.LocalDateTime;

import static controller.HomePage.convSelectionModel;

public class MessagesInAConv {
    @FXML
    ListView<Message> messages_listView;

    public Conversation selectedConv = new Conversation();


    public void loadMessages(ActionEvent actionEvent) {
        selectedConv = convSelectionModel.getSelectedItem();
        //++++ temp
        selectedConv.addMessage(new Message(new User("mamad@gmail.com"),new User("mataghi@gmail.com"),"sdhflkasjdflsdf", LocalDateTime.now(),"subj",new byte[1], MessageType.MAIL));
        messages_listView.setItems(FXCollections.observableArrayList(selectedConv.getMessagess()));
        messages_listView.setCellFactory(messageListView -> new MessagesListItem());
    }

    public void back(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("HomePage");
    }
}
