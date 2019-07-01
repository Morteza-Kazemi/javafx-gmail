package controller.conversationsController;

import controller.PageLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.messaging.Conversation;
import model.messaging.Message;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConversationListItemController {
    private Conversation conversation;
    @FXML
    private AnchorPane root;
    @FXML
    private ImageView contact_imageView;
    @FXML
    private Label subject_label;
    public ConversationListItemController(Conversation conversation) {
        this.conversation = conversation;
        try {
            new PageLoader().load("conversation_listview",this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AnchorPane init(){
        Message message = conversation.getFirstMessage();
        subject_label.setText(message.getSubject());
//        String url = conversation.get.getUrl;
//        if(!Files.exists(Paths.get(url)));{
//            url = UNDEFINED_PROFILE_PHOTO;
//        }
        return root;
    }
}
