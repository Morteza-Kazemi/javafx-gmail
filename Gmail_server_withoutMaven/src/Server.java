import model.messaging.Message;
import model.messaging.MessageType;
import model.user.User;
import model.user.UserAccount;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    public static HashSet<User> usersList = new HashSet<>();//a good and fast hashcode method is implemented.
    static{
        try (
                FileInputStream fileInputStream = new FileInputStream("database/allUsers.txt");
                ObjectInputStream ois = new ObjectInputStream(fileInputStream);
        ) {
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
        while (!serverSocket.isClosed()){
            //this executor service is much more efficient than the classic way.
            try {
                exeService.execute(new ServerRunner(serverSocket.accept()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        saveToDatabase(usersList);
    }

    private void saveToDatabase(HashSet<User> usersList) {
        try (
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("database/allUsers.txt"));
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
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void handle(Message message) throws IOException {

        switch (message.getMessageType()){
            case SIGN_IN:
                UserAccount account =  (UserAccount) message.getObject();
                for (User user : Server.usersList) {
                    if(user.getAccount().getUserName().equals(account.getUserName())
                            && user.getAccount().getPassword().equals(account.getPassword())){
                        objostream.writeObject(new Message(MessageType.ACCEPTED,user));
                        break;
                    }
                }
                objostream.writeObject(new Message(MessageType.REJECTED));
                break;
            case SIGN_UP://check if it was in your list of users reject the sign up message.
                User newUser = new User((UserAccount) message.getObject());
                Message answer;
                if(Server.usersList.contains(newUser)){
                    answer = new Message(MessageType.REJECTED);
                }
                else{
                    answer = new Message(MessageType.ACCEPTED);
                    Server.usersList.add(newUser);
                    System.out.println("signup completed");
                    System.out.flush();
                }
                objostream.writeObject(answer);
                objostream.flush();
                break;
            case MAIL:break;
        }
    }
}