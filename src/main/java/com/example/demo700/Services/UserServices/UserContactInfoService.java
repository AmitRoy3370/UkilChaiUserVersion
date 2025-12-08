package com.example.demo700.Services.UserServices;

import java.util.List;

import com.example.demo700.Model.UserModels.UserContactInfo;

public interface UserContactInfoService {
	
	public UserContactInfo addUserContactInfo(UserContactInfo userContactInfo, String userId);
	public List<UserContactInfo> seeAllUserContactInfo();
	public UserContactInfo searchByEmail(String email);
	public UserContactInfo searchByPhone(String phone);
	public UserContactInfo searchByUserId(String userId);
	public UserContactInfo updateUserContactInfo(String userContactInfoId, String userId, UserContactInfo userContactInfo);
	public boolean deleteUserContactInfo(String userContactInfoId, String userId);

}
