package com.example.demo700.Repositories.UserRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.UserModels.AdvocateRating;

@Repository
public interface AdvocateRatingRepository extends MongoRepository<AdvocateRating, String> {

	public List<AdvocateRating> findByUserId(String userId);
	public List<AdvocateRating> findByAdvocateId(String advocateId);
	
	public List<AdvocateRating> findByRatingGreaterThan(int rating);
	
}
