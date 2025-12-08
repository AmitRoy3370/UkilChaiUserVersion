package com.example.demo700.Model.UserModels;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "UserLocation")
public class UserLocation {

	@Id
	private String id;

	@NonNull
	private String userId;

	@NonNull
	private String locationName;

	@NonNull
	private double lattitude;

	@NonNull
	private double longitude;

	public UserLocation(String userId, String locationName, double lattitude, double longitude) {
		super();
		this.userId = userId;
		this.locationName = locationName;
		this.lattitude = lattitude;
		this.longitude = longitude;
	}

	public UserLocation() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public double getLattitude() {
		return lattitude;
	}

	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "UserLocation [id=" + id + ", userId=" + userId + ", locationName=" + locationName + ", lattitude="
				+ lattitude + ", longitude=" + longitude + "]";
	}

}
