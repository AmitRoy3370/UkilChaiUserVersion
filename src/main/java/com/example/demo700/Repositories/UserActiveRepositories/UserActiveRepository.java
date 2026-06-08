package com.example.demo700.Repositories.UserActiveRepositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.UserActiveModel.UserActive;

@Repository
public interface UserActiveRepository extends MongoRepository<UserActive, String> {

	public UserActive findByUserId(String userId);

	public List<UserActive> findByActive(boolean active);

	// Manual cleanup - using @Query for delete operation
	@Query(value = "{ 'lastActivity': { $lt: ?0 } }", delete = true)
	public long deleteByLastActivityBefore(Date expiryTime);
	
	// Check expired records
    @Query("{ 'lastActivity': { $lt: ?0 } }")
    List<UserActive> findExpiredRecords(Date expiryTime);
    
    // Count active users in last N seconds
    @Query(value = "{ 'lastActivity': { $gt: ?0 } }", count = true)
    long countActiveUsers(Date since);
    
    // Update last activity
    @Query("{ 'userId': ?0 }")
    @Update("{ '$set': { 'lastActivity': ?1 } }")
    void updateLastActivity(String userId, Date lastActivity);
}