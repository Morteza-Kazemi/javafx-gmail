package controller;

import controller.conversationsController.ConversationListItem;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.messaging.Conversation;

import javafx.fxml.FXML;
import model.messaging.Message;
import model.messaging.MessageType;
import model.user.User;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HomePage {
    //setting the conversations... :
    @FXML
    ListView<Conversation> conversations_listview;
    @FXML
    ImageView profilePhoto_imageView ;

    //++++++ this should be available here...
    List<Conversation> convList = new ArrayList<>();
    User currentUser = Connection.getConnectedUser();

    //++++ this is just for testing the application:
    Conversation temp = new Conversation();

    public void showConversations() {
        //+++ this is for a temporary test.
        initializeTestConversationList();
        conversations_listview.setItems(FXCollections.observableArrayList(currentUser.getConversations()));
        conversations_listview.setCellFactory(conversationListView -> new ConversationListItem());
        //++++++ shows profile photo:
//        String uri = Connection.getConnectedUser().getAccount().getProfilePhotoFile().toURI().toString();
//        profilePhoto_imageView.setImage(new Image(uri));
    }

    public void initializeTestConversationList(){
        Conversation conv1 = new Conversation();
        Message message1 = new Message(MessageType.MAIL);
        message1.setSubject("this is first subject!");
        conv1.addMessage(message1);

        Conversation conv2 = new Conversation();
        Message message2 = new Message(MessageType.MAIL);
        message2.setSubject("this is second subject!");
        conv2.addMessage(message2);
        convList.add(conv1);
        convList.add(conv2);
        currentUser.setConversations(convList);
    }

    public void showSettings(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("settings");
    }

    public void showStarredConversations(ActionEvent actionEvent) {
    }

    public void showDrafts(ActionEvent actionEvent) {
    }

    public void compose(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("compose");
    }
}
