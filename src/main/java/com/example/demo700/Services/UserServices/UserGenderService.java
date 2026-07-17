package com.example.demo700.Services.UserServices;

import java.util.List;

import com.example.demo700.ENums.Gender;
import com.example.demo700.Model.UserModels.UserGender;

public interface UserGenderService {

	public UserGender addUserGender(UserGender userGender, String userId);
	public UserGender updateUserGender(UserGender userGender, String userId, String id);
	
	public UserGender findById(String id);
	public List<UserGender> findAll();
	public UserGender findByUserId(String userId);
	public List<UserGender> findByGender(Gender gender);
	public List<UserGender> findByUserIdIn(List<String> usersId);
	
	public boolean removeUserGender(String id, String userId);
	
}
