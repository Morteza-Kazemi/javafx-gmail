package controller.conversationsController;

import controller.PageLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.messaging.Conversation;
import model.messaging.Message;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConversationListItemController {
    private Conversation conversation;
    @FXML
    private AnchorPane root;
    @FXML
    private ImageView contact_imageView;
    @FXML
    private Label subject_label;
    @FXML private Label text_label;
    @FXML private Label time_label;
    @FXML private Label sender_label;
    @FXML private Label reciever_label;


    public ConversationListItemController(Conversation conversation) {
        this.conversation = conversation;
        try {
            new PageLoader().load("conversation_listview",this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    LocalDateTime localDateTime;

    public AnchorPane init(){
        Message message = conversation.getFirstMessage();
        subject_label.setText(message.getSubject());

        String text = message.getText();
        if(text != null && !text.isEmpty()){
            text = text.substring(0,text.length()>5 ? 5 : text.length());
            text_label.setText(message.getText().substring(0,5));
        }
        //time label:
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd  HH:mm:ss");
        String formatDateTime = message.getDate().format(formatter);
        time_label.setText(formatDateTime);
//        String url = conversation.get.getUrl;
//        if(!Files.exists(Paths.get(url)));{
//            url = UNDEFINED_PROFILE_PHOTO;
//        }
        return root;
    }
    public void show_messagesOfConversation() throws IOException {
        new PageLoader().load("messagesInAConv");
    }
}
