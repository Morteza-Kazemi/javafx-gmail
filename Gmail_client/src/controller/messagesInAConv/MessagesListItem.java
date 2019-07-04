package controller.messagesInAConv;

import controller.conversationsController.ConversationListItemController;
import javafx.scene.control.ListCell;
import model.messaging.Message;

public class MessagesListItem extends ListCell<Message>{

        @Override
        public void updateItem(Message message,boolean empty){
            super.updateItem(message,empty);
            if(message!=null){
                setStyle("-fx-background-color: #adb4bc");
                try{
                    setGraphic(new MessagesListItemController(message).init());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

}
