package client.service;

import java.io.ObjectInputStream;
import java.net.Socket;
import client.common.Message;

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
