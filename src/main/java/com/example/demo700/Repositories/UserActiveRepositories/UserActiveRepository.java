package com.example.demo700.Repositories.UserActiveRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.UserActiveModel.UserActive;

@Repository
public interface UserActiveRepository extends MongoRepository<UserActive, String> {

	public UserActive findByUserId(String userId);
	public List<UserActive> findByActive(boolean active);
	
}
