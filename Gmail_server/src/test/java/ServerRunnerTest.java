import model.messaging.Conversation;
import model.messaging.Message;
import model.messaging.MessageType;
import model.user.User;
import model.user.UserAccount;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.Socket;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ServerRunnerTest {

    User user1, user2,invalidUser;
    Socket socket1, socket2,socket3;

    ObjectInputStream ois1, ois2,ois3;
    ObjectOutputStream oos1, oos2,oos3;
    Message mail1, mail2;
    List<Conversation> conversations = new ArrayList<>();
    byte[] testBytes;

    @Before
    public void setUp() throws Exception {
        user1 = new User(new UserAccount("a", "A", "aA", LocalDate.ofYearDay(2000, 12), "aaaaaaaA8"));
        user2 = new User(new UserAccount("b", "B", "bB", LocalDate.ofYearDay(2000, 12), "aaaaaaaA8"));
        //repeated user:
        invalidUser = new User(new UserAccount("w", "d", "bB", LocalDate.ofYearDay(1990, 12), "sdlfjlkASDF345"));

        socket1 = new Socket("localhost", 8888);//for first user.
        socket2 = new Socket("localhost", 8888);//for second user.
        socket3 = new Socket("localhost", 8888);//for second user.
        oos1 = new ObjectOutputStream(socket1.getOutputStream());
        ois1 = new ObjectInputStream(socket1.getInputStream());
        oos2 = new ObjectOutputStream(socket2.getOutputStream());
        ois2 = new ObjectInputStream(socket2.getInputStream());
        oos3 = new ObjectOutputStream(socket3.getOutputStream());
        ois3 = new ObjectInputStream(socket3.getInputStream());

        //+++++ test invalid users ...
        testBytes = "This is assumed to have the bytes of the photo".getBytes();
    }

    @After
    public void tearDown() throws Exception {
        try(
               FileOutputStream fos = new FileOutputStream(new File("database/allUsers.txt"));
               ObjectOutputStream oos = new ObjectOutputStream(fos);
                ){
            oos.writeObject(null);
        }
    }

//    @Test
//    public void run() {
//
//    }

    @Test
    public void handle() throws IOException, ClassNotFoundException {
        testSignUps();
        testSignIns();
//        LocalDateTime nowLDT = LocalDateTime.now();
//        mail1 = new Message(user1, user2, "text1", nowLDT, "subj1", testBytes, MessageType.MAIL);//user1 sends.
//        mail2 = new Message(user2, user1, "text2", nowLDT, "subj2", testBytes, MessageType.MAIL);//user2 sends.
//        MessageType.
//        conversations.add(new Conversation().addMessage(new Message());)


    }

    private void testSignIns() {
        try {
            oos3.writeObject(new Message(MessageType.SIGN_IN, user1));
            Object answer = ois3.readObject();
            assertTrue(answer instanceof Message);
            assertEquals(MessageType.ACCEPTED, ((Message) answer).getMessageType());
            assertEquals(((Message) answer).getObject(),user1);
        } catch (Exception e) {
            fail();
        }

        //sign in for an unavailable user.
    }

    public void testSignUps() {
        try {
            oos1.writeObject(new Message(MessageType.SIGN_UP, user1));
            Object answer = ois1.readObject();
            assertTrue(answer instanceof Message);
            assertEquals(MessageType.ACCEPTED, ((Message) answer).getMessageType());
        } catch (Exception e) {
            fail();
        }

        try {
            oos2.writeObject(new Message(MessageType.SIGN_UP, user2));
            Object answer = ois2.readObject();
            assertTrue(answer instanceof Message);
            assertEquals(MessageType.ACCEPTED, ((Message) answer).getMessageType());
        } catch (Exception e) {
            fail();
        }

        try {
            oos3.writeObject(new Message(MessageType.SIGN_UP, invalidUser));
            Object answer = ois3.readObject();
            assertTrue(answer instanceof Message);
            assertEquals(MessageType.REJECTED,((Message)answer).getMessageType());
        } catch (Exception e) {
            fail();
        }
    }
}