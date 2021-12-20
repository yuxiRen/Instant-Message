package server.service;

import java.util.HashMap;
import client.service.ClientConnectServerThread;

public class ManageServerConnectClientThread {
    private static HashMap<String, ClientConnectServerThread> map = new HashMap<>();

    public static void addThread(String userId, ServerConnectClientThread sct) {
        map.put(userId, sct);
    }

    public static ClientConnectServerThread getThread(String userId) {
        return map.get(userId);
    }
}
