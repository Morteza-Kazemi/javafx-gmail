package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Connection  {
    private static ObjectInputStream ois;

    private static ObjectOutputStream oos;

    public static ObjectInputStream getOis() {
        return ois;
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

}
