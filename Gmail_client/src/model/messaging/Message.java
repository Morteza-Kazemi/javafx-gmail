package model.messaging;

import model.timing.Date;
import model.user.User;

import java.io.File;

public class Message {
    private User sender;
    private User receiver;
    private File attachedFile;
    private String text;
    private Date date;
    private String subject;
    private MessageState state;
    private MessageType messageType;

    public MessageType getMessageType() {
        return messageType;
    }

    private String object;

    public String getText() {
        return text;
    }

    public Message(MessageType messageType, String text) {//this is used for sign up messages
        this.messageType = messageType;
        this.text = text;
    }
}

enum MessageState {
    SENT,RECIEVED,ERROR_SENDING
}

