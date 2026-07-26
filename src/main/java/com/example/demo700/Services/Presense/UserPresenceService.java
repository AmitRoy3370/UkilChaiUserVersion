package com.example.demo700.Services.Presense;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserPresenceService {

    private final Map<String, Boolean> onlineUsers = new ConcurrentHashMap<>();

    private static final String cacheValue = "UserPresence";
    
    @CacheEvict(value = cacheValue, allEntries = true)
    public void userConnected(String userId) {
        onlineUsers.put(userId, true);
    }
    
    @CacheEvict(value = cacheValue, allEntries = true)
    public void userDisconnected(String userId) {
        onlineUsers.remove(userId);
    }

    @Cacheable(value = cacheValue, key = "'findByUserId_' + #userId")
    public boolean isOnline(String userId) {
        return onlineUsers.containsKey(userId);
    }
}