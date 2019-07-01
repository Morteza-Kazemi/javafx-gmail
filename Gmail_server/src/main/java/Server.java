import model.messaging.Message;
import model.messaging.MessageType;
import model.user.User;
import model.user.UserAccount;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//++++++++++ vaghti ye socket baste mishe mige socket is closed va baadesh dg nemishe ye nafar dige roo in ip o port vasl beshe be server
public class Server implements Runnable {
    public static HashSet<User> usersList = new HashSet<>();//a good and fast hashcode method is implemented.

    public static final String DATABASE_USERS = "src\\main\\java\\database\\allUsers.txt";

    static{
        try (
//                FileOutputStream outputStream = new FileOutputStream("src\\main\\java\\database\\allUsers.txt");
//                ObjectOutputStream oos = new ObjectOutputStream(outputStream);
                FileInputStream fileInputStream = new FileInputStream(DATABASE_USERS);
                ObjectInputStream ois = new ObjectInputStream(fileInputStream);
        ) {
//            oos.writeObject(null);
            Object o = ois.readObject();
            if(o!=null){
                usersList = (HashSet<User>) o;
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }finally {
            saveToDatabase(usersList);
        }
    }

    public static void saveToDatabase(HashSet<User> usersList) {
        try (
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATABASE_USERS));
        ) {
           oos.writeObject(usersList);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

class ServerRunner implements Runnable{
    private Socket serverListener;
    private ObjectOutputStream objostream;
    private ObjectInputStream objistream;
    public ServerRunner(Socket serverListener){
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
        while(!serverListener.isClosed()){
            try {
                Message message = (Message) objistream.readObject();
                handle(message);
            }
            catch (SocketException se){
                try {
                    Server.saveToDatabase(Server.usersList);
                    serverListener.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void handle(Message message) throws IOException {

        Lock lock = new ReentrantLock();
        switch (message.getMessageType()){
            case SIGN_IN:
                UserAccount account =  (UserAccount) message.getObject();
                lock.lock();
                for (User user : Server.usersList) {
                    if(user.getAccount().getUserName().equals(account.getUserName())
                            && user.getAccount().getPassword().equals(account.getPassword())){
                        objostream.writeObject(new Message(MessageType.ACCEPTED,user));
                        break;
                    }
                }
                lock.unlock();
                objostream.writeObject(new Message(MessageType.REJECTED));
                //++++ fluch after each writing in outputstreams:
                objostream.flush();
                break;
            case SIGN_UP://check if it was in your list of users reject the sign up message.
                User newUser = new User((UserAccount) message.getObject());
                Message answer;
                lock.lock();
                if(Server.usersList.contains(newUser)){
                    answer = new Message(MessageType.REJECTED);
                }
                else{
                    answer = new Message(MessageType.ACCEPTED);
                    Server.usersList.add(newUser);
                    System.out.println("signup completed");
                    System.out.flush();
                }
                lock.unlock();
                objostream.writeObject(answer);
                objostream.flush();
                break;
            case MAIL:break;
        }
    }
}