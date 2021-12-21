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

    public static String getOnlineUserList() {
        StringBuilder sb = new StringBuilder();
        for (String userId : map.keySet()) {
            sb.append(userId).append(" ");
        }
        return sb.toString();
    }

    public static void delete(String userId) {
        map.remove(userId);
    }

    public static HashMap<String, ServerConnectClientThread> getMap() {
        return map;
    }
}
