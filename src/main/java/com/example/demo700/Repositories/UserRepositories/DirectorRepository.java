package com.example.demo700.Repositories.UserRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.UserModels.Director;

@Repository
public interface DirectorRepository extends MongoRepository<Director, String> {

	public Director findByUserId(String userId);
	public List<Director> findByNid(String nid);
	public List<Director> findByPositionContainingIgnoreCase(String position);
	
}
