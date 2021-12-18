package client.service;

import java.util.HashMap;

public class ManageClientConnectServerThread {
    // key: userId, value: thread
    private static HashMap<String, ClientConnectServerThread> map = new HashMap<>();

    public static void addThread(String userId, ClientConnectServerThread thread) {
        map.put(userId, thread);
    }

    public static ClientConnectServerThread getThread(String userId) {
        return map.get(userId);
    }
}
