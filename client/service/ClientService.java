package service;

import java.io.IOException;
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

    // ask Server for online friends list
    public void onlineUsersList() {
        Message message = new Message();
        message.setType(MessageType.GET_ONLINE_USERS);
        message.setSender(user.getUserId());
        try {
            ClientConnectServerThread thread =
                    ManageClientConnectServerThread.getThread(user.getUserId());
            ObjectOutputStream oos = new ObjectOutputStream(thread.getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // exit Client side, and send Server a exit message
    public void logout() {
        Message message = new Message();
        message.setType(MessageType.CLIENT_EXIT);
        message.setSender(user.getUserId());
        try {
            ClientConnectServerThread thread =
                    ManageClientConnectServerThread.getThread(user.getUserId());
            ObjectOutputStream oos = new ObjectOutputStream(thread.getSocket().getOutputStream());
            oos.writeObject(message);
            System.out.println(user.getUserId() + " logout");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
