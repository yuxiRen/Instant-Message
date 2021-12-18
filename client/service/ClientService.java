package client.service;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import client.common.Message;
import client.common.MessageType;
import client.common.User;

public class ClientService {
    private User user = new User();
    private Socket socket;

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
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
