package com.example.demo700.Services.UserServices;

import java.util.List;

import com.example.demo700.Model.UserModels.UserLocation;

public interface UserlocationService {

	public UserLocation addUserLocation(UserLocation userLocation);

	public List<UserLocation> seeAllUserLocation();

	public UserLocation updateUserLocation(UserLocation userLocation, String userId, String userLocationId);

	public boolean deleteUserLocation(String userLocationId, String userId);

	public UserLocation findByUserId(String userId);

	public List<UserLocation> findByLocationName(String locationName);

	public List<UserLocation> findByLattitudeAndLongitude(double lattitude, double longitude);

}
