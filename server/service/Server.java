package server.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import client.common.Message;
import client.common.MessageType;
import client.common.User;

public class Server {
    private ServerSocket socket = null;// wait for incoming client connection request

    public Server() {
        try {
            socket = new ServerSocket(9999);
            while (true) {
                Socket s = socket.accept();
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                User user = (User) ois.readObject();
                Message message = new Message();
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                if (user.getUserId().equals("100") && user.getPassword().equals("123456")) {
                    message.setType(MessageType.LOGIN_SUCCEED);
                    oos.writeObject(message);
                    // create a thread to communicate with client
                    ServerConnectClientThread sct =
                            new ServerConnectClientThread(s, user.getUserId());
                    sct.start();
                    ManageServerConnectClientThread.addThread(user.getUserId(), sct);
                } else {
                    message.setType(MessageType.LOGIN_FAILED);
                    oos.writeObject(message);
                    s.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
