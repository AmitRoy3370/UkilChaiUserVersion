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
        try {
            System.out.println("🔄 Starting TTL index creation for UserActive...");
            
            IndexOperations indexOps = mongoTemplate.indexOps(UserActive.class);
            
            // Check if index already exists
            boolean indexExists = false;
            try {
                indexExists = indexOps.getIndexInfo().stream()
                    .anyMatch(info -> "lastActivity_ttl".equals(info.getName()));
            } catch (Exception e) {
                System.out.println("⚠️ Could not check existing indexes: " + e.getMessage());
            }
            
            if (!indexExists) {
                try {
                    // Create TTL index properly
                    indexOps.ensureIndex(new Index()
                        .on("lastActivity", org.springframework.data.domain.Sort.Direction.ASC)
                        .expire(60, TimeUnit.SECONDS)
                        .named("lastActivity_ttl"));
                    
                    System.out.println("✅ TTL index created on UserActive.lastActivity");
                } catch (Exception e) {
                    System.out.println("❌ Failed to create TTL index: " + e.getMessage());
                    System.out.println("⚠️ Will rely on manual cleanup only");
                }
            } else {
                System.out.println("✅ TTL index already exists on UserActive.lastActivity");
            }
            
            // Verify index
            verifyIndex();
            
        } catch (Exception e) {
            System.out.println("❌ Error in ensureTTLIndex: " + e.getMessage());
            // Don't throw exception - allow app to start with manual cleanup only
        }
    }
    
    private void verifyIndex() {
        try {
            IndexOperations indexOps = mongoTemplate.indexOps(UserActive.class);
            for (IndexInfo indexInfo : indexOps.getIndexInfo()) {
                if ("lastActivity_ttl".equals(indexInfo.getName())) {
                    System.out.println("✅ Index verified: " + indexInfo);
                    System.out.println("   - Expire after: " + indexInfo.getExpireAfter() + " seconds");
                    return;
                }
            }
            System.out.println("⚠️ TTL index not found, relying on manual cleanup");
        } catch (Exception e) {
            System.out.println("⚠️ Could not verify index: " + e.getMessage());
        }
    }
    
    // Fallback: Manual cleanup every 30 seconds
    @Scheduled(fixedDelay = 30000)
    public void manualCleanup() {
        try {
            Date expiryTime = new Date(System.currentTimeMillis() - 65000); // 65 seconds ago
            long deletedCount = userActiveRepository.deleteByLastActivityBefore(expiryTime);
            if (deletedCount > 0) {
                System.out.println("🔄 Manual cleanup: Removed " + deletedCount + " expired records at " + new Date());
            }
        } catch (Exception e) {
            System.out.println("❌ Error in manual cleanup: " + e.getMessage());
        }
    }
}