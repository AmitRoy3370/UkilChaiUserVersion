package com.example.demo700.Repositories.UserLiveLocationRepositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo700.Model.LiveLocations.LiveLocationData;

@Repository
public interface UserLiveLocationRepository extends MongoRepository<LiveLocationData, String> {

	public LiveLocationData findByAdvocateId(String advocateId);
	public LiveLocationData findByUserId(String userId);
	public List<LiveLocationData> findByLocationNameContainingIgnoreCase(String locationName);
	public List<LiveLocationData> findByAdvocateIdIn(List<String> advocatesId);
	public List<LiveLocationData> findByUserIdIn(List<String> usersId);
	
}
