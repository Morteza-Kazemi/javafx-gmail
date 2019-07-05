package server;

import model.user.User;
import java.io.*;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//++++++++++ vaghti ye socket baste mishe mige socket is closed va baadesh dg nemishe ye nafar dige roo in ip o port vasl beshe be server
//+++++//***** write javadoc for some methods that need it.
public class Server implements Runnable {
    //*****
    public static CopyOnWriteArrayList<User> usersList = new CopyOnWriteArrayList<>();//a good and fast hashcode method is implemented.

    public static final String DATABASE_USERS = "src\\main\\java\\database\\allUsers.txt";

    static{
        try (
                FileOutputStream outputStream = new FileOutputStream("src\\main\\java\\database\\allUsers.txt");
                ObjectOutputStream oos = new ObjectOutputStream(outputStream);
                FileInputStream fileInputStream = new FileInputStream(DATABASE_USERS);
                ObjectInputStream ois = new ObjectInputStream(fileInputStream);
        ) {
            oos.writeObject(null);
            System.out.println("null written");
            Object o = ois.readObject();
            if(o!=null){
                usersList = (CopyOnWriteArrayList<User>) o;
            }
//            optional.ifPresent(a->usersList = (HashSet<User>)a);

        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }


    private static ServerSocket serverSocket ;
    private static final int REQUEST_PORT = 8888;

    public static void main(String[] args) {
        start();
    }

    public static void start(){
        //+++++++++++++++  have I closed all the resources ?!
        try {
            serverSocket = new ServerSocket(REQUEST_PORT);
            Thread serverThread = new Thread(new Server(),"server thread");
            serverThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void run() {
        ExecutorService exeService = Executors.newCachedThreadPool();
        try{
            while (!serverSocket.isClosed()){
                //this executor service is much more efficient than the classic way.
                try {
                    exeService.execute(new ServerRunner(serverSocket.accept()));
                    System.out.println("user connected");
                    ServerRunner.printCurrentTime();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }finally {
            saveToDatabase(usersList);
            System.out.printf("users saved to db");
        }
    }

    //*****
    public static void saveToDatabase(CopyOnWriteArrayList<User> usersList) {
        try (
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATABASE_USERS));
        ) {
           oos.writeObject(usersList);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
