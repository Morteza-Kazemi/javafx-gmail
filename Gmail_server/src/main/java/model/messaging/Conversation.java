package model.messaging;

import model.user.User;
import java.util.ArrayList;

public class Conversation {
    //++++++ which lists should be thread-safe?????
    //++++ make almost everything in every class private.
    private ArrayList<Message> messagess;
    private User conversationStarter;
    private Message firstMessage;


    public ArrayList<Message> getMessagess() {
        return messagess;
    }

    public Message getFirstMessage() {
        if(messagess.size()>0){
            return messagess.get(0);
        }
        return null;
    }


    public User getConversationStarter() {
        return conversationStarter;
    }

    public Conversation() {
        messagess = new ArrayList<>();
    }
    ConversationState state;

    public void addMessage(Message message){
        messagess.add(message);
    }
}

enum ConversationState{
    IMPORTANT,READ,UNREAD
}