package service;

import java.util.HashMap;

public class ManageServerConnectClientThread {
    private static HashMap<String, ServerConnectClientThread> map = new HashMap<>();

    public static void addThread(String userId, ServerConnectClientThread sct) {
        map.put(userId, sct);
    }

    public static ServerConnectClientThread getThread(String userId) {
        return map.get(userId);
    }
}
