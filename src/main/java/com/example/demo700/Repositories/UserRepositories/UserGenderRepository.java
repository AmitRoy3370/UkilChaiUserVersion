package com.example.demo700.Repositories.UserRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.ENums.Gender;
import com.example.demo700.Model.UserModels.UserGender;

@Repository
public interface UserGenderRepository extends MongoRepository<UserGender, String> {

	public UserGender findByUserId(String userId);
	public List<UserGender> findByGender(Gender gender);
	public List<UserGender> findByUserIdIn(List<String> usersId);
	
}
