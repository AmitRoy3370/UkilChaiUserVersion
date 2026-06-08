package com.example.demo700.Services.Presense;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserPresenceService {

    private final Map<String, Boolean> onlineUsers = new ConcurrentHashMap<>();

    public void userConnected(String userId) {
        onlineUsers.put(userId, true);
    }

    public void userDisconnected(String userId) {
        onlineUsers.remove(userId);
    }

    public boolean isOnline(String userId) {
        return onlineUsers.containsKey(userId);
    }
}