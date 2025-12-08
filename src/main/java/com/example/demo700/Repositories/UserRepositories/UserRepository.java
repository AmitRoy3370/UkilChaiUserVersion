package com.example.demo700.Repositories.UserRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.UserModels.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	
	public User findByName(String name);
	public List<User> findByProfileImageId(String profileImageId);

}
