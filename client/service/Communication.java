package service;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import common.Message;
import common.MessageType;

public class Communication {
    public void sendContent(String content, String senderId, String receiverId) {
        Message message = new Message();
        message.setType(MessageType.COMMON_MESSAGE);
        message.setContent(content);
        message.setSender(senderId);
        message.setReceiver(receiverId);
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime myDateObj = LocalDateTime.now();
        message.setSendTime(myDateObj.format(myFormatObj));
        System.out.println(senderId + " says to " + receiverId + ": ");
        try {
            ClientConnectServerThread thread = ManageClientConnectServerThread.getThread(senderId);
            ObjectOutputStream oos = new ObjectOutputStream(thread.getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToAll(String content, String senderId) {
        Message message = new Message();
        message.setType(MessageType.GROUP_MESSAGE);
        message.setContent(content);
        message.setSender(senderId);
        message.setSendTime(new Date().toString());
        try {
            ClientConnectServerThread thread = ManageClientConnectServerThread.getThread(senderId);
            ObjectOutputStream oos = new ObjectOutputStream(thread.getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
