package com.example.demo700.Services.UserActiveServices;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.Query;

import com.example.demo700.Model.UserActiveModel.UserActive;

public interface UserActiveService {

	public UserActive addUserActive(UserActive userActive);

	public UserActive updateUserActive(UserActive userActive, String userId, String id);

	public UserActive findById(String id);

	public List<UserActive> findAll();

	public UserActive findByUserId(String userId);

	public List<UserActive> findByActive(boolean active);

	public List<UserActive> findExpiredRecords(Date expiryTime);

	public long countActiveUsers(Date since);

	public void updateLastActivity(String userId, Date lastActivity);

	public boolean removeUserActive(String id, String userId);

}
