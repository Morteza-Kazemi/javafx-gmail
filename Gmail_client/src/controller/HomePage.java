package controller;

import controller.conversationsController.ConversationListItem;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import model.messaging.Conversation;

import javafx.fxml.FXML;
import model.messaging.Message;
import model.messaging.MessageType;
import model.user.User;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
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
    @FXML
    TextField subjSearch_textfield;
    @FXML
    TextField usernameSearch_textfield;


    Boolean searchMode = false;

    public static SelectionModel<Conversation> convSelectionModel;
    //++++++ this should be available here...
    List<Conversation> convList = new ArrayList<>();
    User currentUser = Connection.getConnectedUser();
    ObjectOutputStream oos = Connection.getOos();
    ObjectInputStream ois = Connection.getOis();

    //++++ this is just for testing the application:
    Conversation temp = new Conversation();

    public void showConversations() throws IOException, ClassNotFoundException {
        showProfilePhoto();
        //+++ this is for a temporary test.
//        initializeTestConversationList();
        oos.writeObject(new Message(MessageType.GET_CONVERSATIONS, currentUser));
        oos.flush();
        Message readObject = (Message) ois.readObject();
        List<Conversation> list = (List<Conversation>) (readObject.getObject());
        currentUser.setConversations(list);
        System.out.println("conversations length of " + currentUser.getAddress() + currentUser.getConversations().size());
        String searchedSubj = subjSearch_textfield.getText();
        String searchedUsername = usernameSearch_textfield.getText();
        if (searchMode && (!searchedSubj.isEmpty() || !searchedUsername.isEmpty())) {
            List<Conversation> newList = new ArrayList<>();
            for (Conversation conversation : list) {
                for (Message message : conversation.getMessagess()) {
                    if (searchedUsername.isEmpty()) {//so subj field is not empty
                        if (message.getSubject().contains(searchedSubj)) {
                            newList.add(conversation);
                        }
                    } else if (searchedSubj.isEmpty()) {
                        if (message.getSender().getAddress().contains(searchedUsername)) {
                            newList.add(conversation);
                        }
                    } else {
                        if (message.getSender().getAddress().contains(searchedUsername)
                                && message.getSubject().contains(searchedSubj)) {
                            newList.add(conversation);
                        }
                    }
                }
            }
            setListView(newList);
            searchMode = false;
            }
        else{
                setListView(list);
            }
//++++++    convSelectionModel.setSelectionMode(SelectionMode.SINGLE);

            //++++++ shows profile photo:
//        String uri = Connection.getConnectedUser().getAccount().getProfilePhotoFile().toURI().toString();
//        profilePhoto_imageView.setImage(new Image(uri));
        }

    private void showProfilePhoto() throws IOException {
//        System.out.println(currentUser.getAccount().getProfilePhotoBArr().length);
        try(ByteArrayInputStream bais = new ByteArrayInputStream(currentUser.getAccount().getProfilePhotoBArr())){
            BufferedImage img = ImageIO.read(bais);
            Image image = SwingFXUtils.toFXImage(img,null);
            profilePhoto_imageView.setClip(new Circle(100,100,100));
            profilePhoto_imageView.setImage(image);
        }
    }

    private void setListView (List < Conversation > list) {
            conversations_listview.setItems(FXCollections.observableArrayList(list));
            conversations_listview.setCellFactory(conversationListView -> new ConversationListItem());
            convSelectionModel = conversations_listview.getSelectionModel();
        }


        public void initializeTestConversationList () {
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

        public void showSettings (ActionEvent actionEvent) throws IOException {
            new PageLoader().load("settings");
        }

        public void showStarredConversations (ActionEvent actionEvent){
        }

        public void showDrafts (ActionEvent actionEvent){
        }

        public void compose (ActionEvent actionEvent) throws IOException {
            new PageLoader().load("compose");
        }

    public void search(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        searchMode = true;
        showConversations();
    }
}
