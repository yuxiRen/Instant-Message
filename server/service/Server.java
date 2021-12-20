package service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import common.Message;
import common.MessageType;
import common.User;

public class Server {
    private ServerSocket socket = null;// wait for incoming client connection request
    private static HashMap<String, User> dataBase = new HashMap<>();
    static {
        dataBase.put("100", new User("100", "123456"));
        dataBase.put("200", new User("200", "223456"));
        dataBase.put("300", new User("300", "323456"));
        dataBase.put("400", new User("400", "423456"));
        dataBase.put("500", new User("500", "523456"));
    }

    private boolean checkUser(String userId, String password) {
        if (dataBase.containsKey(userId)) {
            return dataBase.get(userId).getPassword().equals(password);
        } else {
            return false;
        }
    }

    public Server() {
        try {
            System.out.println("Server listens port 9999...");
            socket = new ServerSocket(9999);
            while (true) {
                Socket s = socket.accept();
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());

                User user = (User) ois.readObject();
                Message message = new Message();

                if (checkUser(user.getUserId(), user.getPassword())) {
                    message.setType(MessageType.LOGIN_SUCCEED);
                    oos.writeObject(message);
                    // create a thread to communicate with client
                    ServerConnectClientThread sct =
                            new ServerConnectClientThread(s, user.getUserId());
                    sct.start();
                    ManageServerConnectClientThread.addThread(user.getUserId(), sct);
                } else {
                    System.out.println(
                            "用户 id=" + user.getUserId() + " pwd=" + user.getPassword() + " 验证失败");

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
