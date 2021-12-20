package service;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import common.Message;
import common.MessageType;
import common.User;

public class ClientService {
    private User user = new User();
    private Socket socket;
    private ClientConnectServerThread clientConnectServerThread;
    private boolean loginSucceeded = false;

    public boolean checkUser(String userId, String password) {
        // create user
        user.setUserId(userId);
        user.setPassword(password);
        // connect to server, send user
        try {
            // build communication tube
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
            // send user
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(user);
            // get message from server
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message message = (Message) ois.readObject();
            if (message.getType().equals(MessageType.LOGIN_SUCCEED)) {
                clientConnectServerThread = new ClientConnectServerThread(socket);
                clientConnectServerThread.start();
                ManageClientConnectServerThread.addThread(userId, clientConnectServerThread);
                loginSucceeded = true;
            } else {
                socket.close();
                loginSucceeded = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginSucceeded;
    }
}
