package service;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Set;
import common.Message;
import common.MessageType;

public class ServerConnectClientThread extends Thread {
    private Socket socket;
    private String userId;

    public ServerConnectClientThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Server connects to Client " + userId);
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                if (message.getType().equals(MessageType.GET_ONLINE_USERS)) {
                    System.out.println(message.getSender() + " asks for online user list.");
                    String onlineUserList = ManageServerConnectClientThread.getOnlineUserList();
                    Message userListMessage = new Message();
                    userListMessage.setType(MessageType.RETURN_ONLINE_USERS);
                    userListMessage.setContent(onlineUserList);
                    userListMessage.setReceiver(message.getSender());
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(userListMessage);
                } else if (message.getType().equals(MessageType.CLIENT_EXIT)) {
                    System.out.println(message.getSender() + " logout");
                    ManageServerConnectClientThread.delete(userId);
                    socket.close();
                    break;
                } else if (message.getType().equals(MessageType.COMMON_MESSAGE)) {
                    ServerConnectClientThread thread =
                            ManageServerConnectClientThread.getThread(message.getReceiver());
                    ObjectOutputStream oos =
                            new ObjectOutputStream(thread.getSocket().getOutputStream());
                    oos.writeObject(message);
                } else if (message.getType().equals(MessageType.GROUP_MESSAGE)) {
                    HashMap<String, ServerConnectClientThread> map =
                            ManageServerConnectClientThread.getMap();
                    Set<String> ids = map.keySet();
                    for (String id : ids) {
                        if (!id.equals(message.getSender())) {
                            ServerConnectClientThread thread =
                                    ManageServerConnectClientThread.getThread(id);
                            ObjectOutputStream oos =
                                    new ObjectOutputStream(thread.getSocket().getOutputStream());
                            oos.writeObject(message);
                        }
                    }
                } else if (message.getType().equals(MessageType.FILE_MESSAGE)) {
                    ServerConnectClientThread thread =
                            ManageServerConnectClientThread.getThread(message.getReceiver());
                    ObjectOutputStream oos =
                            new ObjectOutputStream(thread.getSocket().getOutputStream());
                    oos.writeObject(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
