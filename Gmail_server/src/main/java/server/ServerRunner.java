package server;


import model.messaging.Message;
import model.messaging.MessageType;
import model.user.User;
import model.user.UserAccount;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ServerRunner implements Runnable {
    private Socket serverListener;
    private ObjectOutputStream objostream;
    private ObjectInputStream objistream;

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

    @Override
    public void run() {
        while (!serverListener.isClosed()) {
            try {
                Message message = (Message) objistream.readObject();
                handle(message);
            } catch (SocketException se) {
                try {
                    Server.saveToDatabase(Server.usersList);
                    serverListener.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void handle(Message message) throws IOException {
        //*****
        Lock lock = new ReentrantLock();
        switch (message.getMessageType()) {
            case SIGN_IN:
                UserAccount account = (UserAccount) message.getObject();
                lock.lock();
                for (User user : Server.usersList) {
                    if (user.getAccount().getUserName().equals(account.getUserName())
                            && user.getAccount().getPassword().equals(account.getPassword())) {
//                        System.out.println("sent user file:"+user.getAccount().getProfilePhotoBArr().length);
                        objostream.writeObject(new Message(MessageType.ACCEPTED, user));
                        break;
                    }
                }
                lock.unlock();
                objostream.writeObject(new Message(MessageType.REJECTED));
                //++++ fluch after each writing in outputstreams:
                objostream.flush();
                break;
            case SIGN_UP://check if it was in your list of users reject the sign up message.
                User newUser = (User) message.getObject();
                Message answer;
                lock.lock();
                if (Server.usersList.contains(newUser)) {
                    answer = new Message(MessageType.REJECTED);
                } else {
                    answer = new Message(MessageType.ACCEPTED);
                }
                lock.unlock();
                objostream.writeObject(answer);
                objostream.flush();
                break;
            case MAIL:
                break;
            case SAVE_USER:
                User user = (User) message.getObject();
                System.out.println("user changes saved : " + Server.usersList.remove(user));
                Server.usersList.add(user);
                System.out.println("saved file:" + user.getAccount().getUserName());
                break;

        }
    }
}
