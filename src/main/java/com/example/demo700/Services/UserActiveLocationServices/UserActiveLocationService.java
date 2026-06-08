package com.example.demo700.Services.UserActiveLocationServices;

import java.util.List;

import com.example.demo700.DTOFiles.UserLiveLocationDataResponse;
import com.example.demo700.Model.LiveLocations.LiveLocationData;

public interface UserActiveLocationService {

	public LiveLocationData addLocation(LiveLocationData liveLocation, String userId);

	public LiveLocationData updateLocation(LiveLocationData liveLocation, String userId, String id);

	public List<UserLiveLocationDataResponse> seeAll();

	public UserLiveLocationDataResponse getById(String id);

	public UserLiveLocationDataResponse findByAdvocateId(String advocateId);

	public UserLiveLocationDataResponse findByUserId(String userId);

	public List<UserLiveLocationDataResponse> findByLocationNameContainingIgnoreCase(String locationName);

	public List<UserLiveLocationDataResponse> findByAdvocateIdIn(List<String> advocatesId);

	public List<UserLiveLocationDataResponse> findByUserIdIn(List<String> usersId);

	public boolean deleteLiveLocation(String id, String userId);

	// New heartbeat methods
	public LiveLocationData heartbeat(String userId, LiveLocationData liveLocation);

	public void removeExpiredLocations();

}
