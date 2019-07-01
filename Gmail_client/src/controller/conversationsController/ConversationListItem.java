package controller.conversationsController;

import javafx.scene.control.ListCell;
import model.messaging.Conversation;

import java.io.IOException;

public class ConversationListItem extends ListCell<Conversation> {
    @Override
    public void updateItem(Conversation conv,boolean empty){
        super.updateItem(conv,empty);
        if(conv!=null){
            setStyle("-fx-background-color: #adb4bc");
            try{
                setGraphic(new ConversationListItemController(conv).init());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
