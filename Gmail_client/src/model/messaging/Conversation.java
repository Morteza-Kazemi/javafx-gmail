package model.messaging;

import model.user.User;

import java.util.ArrayList;

public class Conversation {
    //++++++ which lists should be thread-safe?????
    //++++ make almost everything in every class private.
    private ArrayList<Message> messagess;
    private User conversationStarter;

    public Conversation() {
        messagess = new ArrayList<>();
    }
    ConversationState state;
}

enum ConversationState{
    IMPORTANT,READ,UNREAD
}