package controller;

import controller.conversationsController.ConversationListItem;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.scene.image.ImageView;
import model.messaging.Conversation;

import javafx.fxml.FXML;
import model.messaging.Message;
import model.messaging.MessageType;
import model.user.User;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class HomePage {
    //setting the conversations... :
    @FXML
    ListView<Conversation> conversations_listview;
    @FXML
    ImageView profilePhoto_imageView;

    public static SelectionModel<Conversation> convSelectionModel;
    //++++++ this should be available here...
    List<Conversation> convList = new ArrayList<>();
    User currentUser = Connection.getConnectedUser();
    ObjectOutputStream oos = Connection.getOos();
    ObjectInputStream ois = Connection.getOis();

    //++++ this is just for testing the application:
    Conversation temp = new Conversation();
    public void showConversations() throws IOException, ClassNotFoundException {
        //+++ this is for a temporary test.
//        initializeTestConversationList();
        oos.writeObject(new Message(MessageType.GET_CONVERSATIONS, currentUser));
        oos.flush();
        Message readObject = (Message) ois.readObject();
        List<Conversation> list = (List<Conversation>) (readObject.getObject());
        currentUser.setConversations(list);
        System.out.println("conversations length of " + currentUser.getAddress() + currentUser.getConversations().size());
        conversations_listview.setItems(FXCollections.observableArrayList(currentUser.getConversations()));
        conversations_listview.setCellFactory(conversationListView -> new ConversationListItem());
        convSelectionModel = conversations_listview.getSelectionModel();
//++++++    convSelectionModel.setSelectionMode(SelectionMode.SINGLE);

        //++++++ shows profile photo:
//        String uri = Connection.getConnectedUser().getAccount().getProfilePhotoFile().toURI().toString();
//        profilePhoto_imageView.setImage(new Image(uri));
    }


    public void initializeTestConversationList() {
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
