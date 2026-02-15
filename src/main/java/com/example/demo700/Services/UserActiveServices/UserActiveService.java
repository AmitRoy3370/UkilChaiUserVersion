package com.example.demo700.Services.UserActiveServices;

import java.util.List;

import com.example.demo700.Model.UserActiveModel.UserActive;

public interface UserActiveService {

	public UserActive addUserActive(UserActive userActive);

	public UserActive updateUserActive(UserActive userActive, String userId, String id);

	public UserActive findById(String id);

	public List<UserActive> findAll();

	public UserActive findByUserId(String userId);

	public List<UserActive> findByActive(boolean active);

	public boolean removeUserActive(String id, String userId);

}
