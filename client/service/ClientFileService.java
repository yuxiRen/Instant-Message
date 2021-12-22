package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import common.Message;
import common.MessageType;

public class ClientFileService {
    public void sendFile(String source, String destination, String senderId, String receiverId) {
        Message message = new Message();
        message.setType(MessageType.FILE_MESSAGE);
        message.setSender(senderId);
        message.setReceiver(receiverId);
        message.setFileSource(source);
        message.setFileDestination(destination);
        FileInputStream fileInputStream = null;
        try {
            byte[] fileBytes = new byte[(int) new File(source).length()];
            fileInputStream = new FileInputStream(source);
            fileInputStream.read(fileBytes);
            message.setFileBytes(fileBytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        ClientConnectServerThread thread = ManageClientConnectServerThread.getThread(senderId);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(thread.getSocket().getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(
                "\n" + senderId + " sends " + source + " to " + receiverId + " in " + destination);
    }
}

