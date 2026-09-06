package com.example.demo700.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    private RedisCacheManager cacheManager;

    // এই মেথডটি কল করলে সব Redis Cache মুছে যাবে
    public void clearAllCaches() {
        for (String cacheName : cacheManager.getCacheNames()) {
            if (cacheManager.getCache(cacheName) != null) {
                cacheManager.getCache(cacheName).clear();
            }
        }
        System.out.println("All Redis caches cleared successfully!");
    }
}
