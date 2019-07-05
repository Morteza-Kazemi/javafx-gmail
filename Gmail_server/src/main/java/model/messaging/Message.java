package model.messaging;


import model.user.User;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private User sender;
    private User receiver;
    private File attachedFile;
    private String text;
    private LocalDateTime date;
    private String subject;
    private byte[] attachedFileBArr;
    private User user;

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public byte[] getAttachedFileBArr() {
        return attachedFileBArr;
    }

    public void setAttachedFileBArr(byte[] attachedFileBArr) {
        this.attachedFileBArr = attachedFileBArr;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    private MessageState state;
    private MessageType messageType;
    private Object object;

    public Message(User sender, User receiver, String text, LocalDateTime date, String subject, byte[] attackedFileBArr, MessageType messageType) {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.date = date;
        this.subject = subject;
        this.attachedFileBArr = attackedFileBArr;
        this.messageType = messageType;
    }

    public Message(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getText() {
        return text;
    }

    public Object getObject() {
        return object;
    }

    public Message(MessageType messageType, Object object){//this is used for sign up messages
        this.messageType = messageType;
        this.object = object;
    }

    public Message(MessageType messageType, User user, Object o){
        this.user = user;
        this.messageType = messageType;
    }

    public Message(MessageType messageType,String userName,String phone , String gender , byte[] bytes){
        this.messageType = messageType;
        this.text =userName + "&" + phone + "&"+ gender;
        this.attachedFileBArr = bytes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

enum MessageState {
    SENT,RECIEVED,ERROR_SENDING
}

