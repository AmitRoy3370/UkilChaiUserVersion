package com.example.demo700.Repositories.UserRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.UserModels.UserLocation;

@Repository
public interface UserLocationRepository extends MongoRepository<UserLocation, String> {

	public UserLocation findByUserId(String userId);
	public List<UserLocation> findByLocationNameContainingIgnoreCase(String locationName);
	public List<UserLocation> findByLattitudeAndLongitude(double lattitude, double longitude);
	
	public List<UserLocation> findByUserIdIn(List<String> userIds);
	
}
