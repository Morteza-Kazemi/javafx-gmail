package server;


import model.messaging.Conversation;
import model.messaging.Message;
import model.messaging.MessageType;
import model.user.User;
import model.user.UserAccount;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ServerRunner implements Runnable {
    private Socket serverListener;
    private ObjectOutputStream objostream;
    private ObjectInputStream objistream;
    User mailerDaemon = new User("mailerDaemon@gmail.com");

    public ServerRunner(Socket serverListener) {
        this.serverListener = serverListener;
        try {
            //+++++++ shouldn't I close this resources?!!!!
            objostream = new ObjectOutputStream(serverListener.getOutputStream());
            objistream = new ObjectInputStream(serverListener.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void printCurrentTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        System.out.println("time : "+LocalDateTime.now().format(formatter));
    }

    @Override
    public void run() {
        while (!serverListener.isClosed()) {
            try {
                Message message = (Message) objistream.readObject();
                handle(message);
            } catch (SocketException se) {
                try {
                    Server.saveToDatabase(Server.usersList);
                    System.out.println("users saved to db");
                    serverListener.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void handle(Message message) throws IOException {
        //*****
        Lock lock = new ReentrantLock();
        switch (message.getMessageType()) {
            case SIGN_IN:
                handleSignIn(message, lock);
                break;
            case SIGN_UP://check if it was in your list of users reject the sign up message.
                handleSignUp(message, lock);
                break;
            case MAIL:
                handleMail(message);
                break;
            case SAVE_USER:
                //get the lock before using the list...
                lock.lock();
                User user = (User) message.getObject();
                System.out.println("should be save "+message.getMessageType()+message.getObject());
                //remove the previous user from list.
                System.out.println(user.getAddress() + "user previous data removed : " + Server.usersList.remove(user));
                System.out.println("user new data saved : "+Server.usersList.add(user));
                System.out.println("bytes in server after read obj " + user.getAccount().getProfilePhotoBArr());
                System.out.println("~ phone : "+user.getAccount().getPhoneNumber());
                lock.unlock();
                break;
            case GET_CONVERSATIONS:
                for (User userInList : Server.usersList) {
                    if (userInList.equals(message.getObject())) {
                        objostream.writeObject(new Message(MessageType.GET_CONVERSATIONS, userInList.getConversations()));
                        System.out.println(userInList.getAddress() + "covs were given to client      length:" +userInList.getConversations().size() );
                        break;
                    }
                }
                break;
        }
    }

    private void handleSignUp(Message message, Lock lock) throws IOException {
        User newUser = (User) message.getObject();
        Message answer;
        lock.lock();
        if (Server.usersList.contains(newUser)) {
            answer = new Message(MessageType.REJECTED);
        } else {
            answer = new Message(MessageType.ACCEPTED);
            Server.usersList.add(newUser);

            System.out.println(newUser.getAddress() + "sign up");
            printCurrentTime();

        }
        lock.unlock();
        objostream.writeObject(answer);
        objostream.flush();
    }

    private void handleSignIn(Message message, Lock lock) throws IOException {
        UserAccount account = (UserAccount) message.getObject();
        boolean correctInfo = false;
        lock.lock();
        for (User user : Server.usersList) {
            if (user.getAccount().getUserName().equals(account.getUserName())
                    && user.getAccount().getPassword().equals(account.getPassword())) {
                correctInfo = true;

                System.out.println(user.getAddress() + " sign in");
                printCurrentTime();

                objostream.writeObject(new Message(MessageType.ACCEPTED, user));
                break;
            }
        }
        lock.unlock();
        if (!correctInfo) {
            objostream.writeObject(new Message(MessageType.REJECTED));
            objostream.flush();
            //++++ flush after each writing in outputstreams:
        }
    }

    private void handleMail(Message message) {
        User reciever = null;
        for (User user : Server.usersList) {
            if (user.equals(message.getReceiver())) {
                reciever = user;
                System.out.println(reciever.getAddress() + " : as reciever found ");
                break;
            }
        }
        if (reciever == null) {
            //send a mail from mailer daemon...
        } else {
//            add the message for the receiver;
            Conversation newConversation = new Conversation();
            newConversation.addMessage(message);
            reciever.addConversation(newConversation);
            message.getSender().addConversation(newConversation);
            System.out.println("new size of conversations of " + reciever.getAddress() +" is "+ reciever.getConversations().size());

            System.out.println(reciever.getAddress() + " recieve");
            System.out.println("message : " + message.getSender().getAddress());
            printCurrentTime();

        }
    }
}
