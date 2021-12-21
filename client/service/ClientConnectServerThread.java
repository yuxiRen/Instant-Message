package service;

import java.io.ObjectInputStream;
import java.net.Socket;
import common.Message;
import common.MessageType;

public class ClientConnectServerThread extends Thread {
    private Socket socket;

    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                if (message.getType().equals(MessageType.RETURN_ONLINE_USERS)) {
                    String[] onlineUsers = message.getContent().split(" ");
                    System.out.println("\n============Online Users==============");
                    for (int i = 0; i < onlineUsers.length; i++) {
                        System.out.println("User: " + onlineUsers[i]);
                    }
                } else if (message.getType().equals(MessageType.COMMON_MESSAGE)) {
                    System.out.println("\n" + message.getSendTime() + "\t" + message.getSender()
                            + " says: " + message.getContent());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }


}
