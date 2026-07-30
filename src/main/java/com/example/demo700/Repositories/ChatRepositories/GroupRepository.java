package com.example.demo700.Repositories.ChatRepositories;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.ChatModels.Group;

@Repository
public interface GroupRepository extends MongoRepository<Group, String> {
    
    // একজন ইউজার যেসব গ্রুপের সদস্য সেগুলো খুঁজে বের করা
    @Query("{ 'members' : ?0 }")
    List<Group> findGroupsByMemberId(String userId);
    
    // গ্রুপের নাম দ্বারা খোঁজা
    List<Group> findByGroupNameContainingIgnoreCase(String groupName);
    
    List<Group> findByCreatedBy(String creatorId);
    
}