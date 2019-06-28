import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class Server implements Runnable {
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
        while (!serverSocket.isClosed()){
            try {
                new Thread(new ServerRunner(serverSocket.accept()),"listenerThread").start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class ServerRunner implements Runnable{
    private Socket serverListener;
    public ServerRunner(Socket serverListener){
        this.serverListener = serverListener;
    }

    @Override
    public void run() {

    }
}


class ServerHandler{
    private ObjectInputStream ois;
    private ObjectOutputStream oos;


}
































