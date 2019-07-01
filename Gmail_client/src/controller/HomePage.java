package controller;

import controller.conversationsController.ConversationListItem;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import model.messaging.Conversation;

import javafx.fxml.FXML;
import model.messaging.Message;
import model.messaging.MessageType;

import java.util.ArrayList;
import java.util.List;


public class HomePage {
    //setting the conversations... :
    @FXML
    ListView<Conversation> conversations_listview;
    //++++++ this should be available here...
    List<Conversation> convList = new ArrayList<>();

    //++++ this is just for testing the application:
    Conversation temp = new Conversation();

    public void showConversations() {
        //+++ this is for a temporary test.
        initializeTestConversationList();
        conversations_listview.setItems(FXCollections.observableArrayList(convList));
        conversations_listview.setCellFactory(conversationListView -> new ConversationListItem());

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

    }
}
