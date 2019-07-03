package controller;

import model.user.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Connection  {
    private static ObjectInputStream ois;
    private static ObjectOutputStream oos;


    private static User connectedUser = null;

    public static ObjectInputStream getOis() {
        return ois;
    }

    public static User getConnectedUser() {
        return connectedUser;
    }

    public static void setConnectedUser(User connectedUser) {
        Connection.connectedUser = connectedUser;
    }

    public static ObjectOutputStream getOos() {
        return oos;
    }

    static {
        try {
            ois = new ObjectInputStream(serverIPSetter.clientSocket.getInputStream());
            oos = new ObjectOutputStream(serverIPSetter.clientSocket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getConnextedUser() {
        return connectedUser;
    }

    public void setConnextedUser(User connextedUser) {
        this.connectedUser = connextedUser;
    }
}
