package com.example.demo700.Repositories.UserRepositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.UserModels.UserContactInfo;

@Repository
public interface UserContactInfoRepository extends MongoRepository<UserContactInfo, String> {

	public UserContactInfo findByUserId(String userId);
	public UserContactInfo findByEmail(String email);
	public UserContactInfo findByPhone(String phone);
	
}
