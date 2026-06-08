package com.example.demo700.Security;

import com.example.demo700.Model.UserActiveModel.UserActive;
import com.example.demo700.Repositories.UserActiveRepositories.UserActiveRepository;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexInfo;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableScheduling
public class UserActiveMongoConfig {

    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Autowired
    private UserActiveRepository userActiveRepository;

    @PostConstruct
    public void ensureTTLIndex() {
        IndexOperations indexOps = mongoTemplate.indexOps(UserActive.class);
        
        // Check if index already exists
        boolean indexExists = indexOps.getIndexInfo().stream()
            .anyMatch(info -> "lastActivity_ttl".equals(info.getName()));
        
        if (!indexExists) {
            // Create TTL index properly
            indexOps.ensureIndex(new Index()
                .on("lastActivity", org.springframework.data.domain.Sort.Direction.ASC)
                .expire(60, TimeUnit.SECONDS)
                .named("lastActivity_ttl"));
            
            System.out.println("✅ TTL index created on UserActive.lastActivity");
        } else {
            System.out.println("✅ TTL index already exists on UserActive.lastActivity");
        }
        
        // Verify index
        verifyIndex();
    }
    
    private void verifyIndex() {
        IndexOperations indexOps = mongoTemplate.indexOps(UserActive.class);
        for (IndexInfo indexInfo : indexOps.getIndexInfo()) {
            if ("lastActivity_ttl".equals(indexInfo.getName())) {
                System.out.println("✅ Index verified: " + indexInfo);
                System.out.println("   - Expire after: " + indexInfo.getExpireAfter() + " seconds");
                break;
            }
        }
    }
    
    // Fallback: Manual cleanup every 30 seconds
    @Scheduled(fixedDelay = 30000)
    public void manualCleanup() {
        Date expiryTime = new Date(System.currentTimeMillis() - 65000); // 65 seconds ago
        long deletedCount = userActiveRepository.deleteByLastActivityBefore(expiryTime);
        if (deletedCount > 0) {
            System.out.println("🔄 Manual cleanup: Removed " + deletedCount + " expired records");
        }
    }
}