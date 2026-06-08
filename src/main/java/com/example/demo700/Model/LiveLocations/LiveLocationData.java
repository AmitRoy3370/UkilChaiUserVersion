package com.example.demo700.Model.LiveLocations;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "UsersLiveLocation")
public class LiveLocationData {

	@Id
	private String id;

	private String advocateId;

	@NonNull
	private String userId;

	@NonNull
	private String locationName;

	private double lattitude, longitude;
	
	private Date lastHeartbeat;
	
	private boolean active;

	public LiveLocationData(String advocateId, String userId, String locationName, double lattitude,
			double longitude) {
		super();
		this.advocateId = advocateId;
		this.userId = userId;
		this.locationName = locationName;
		this.lattitude = lattitude;
		this.longitude = longitude;
		this.active = true;
		this.lastHeartbeat = new Date();
	}

	public LiveLocationData() {
		super();
		this.active = true;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAdvocateId() {
		return advocateId;
	}

	public void setAdvocateId(String advocateId) {
		this.advocateId = advocateId;
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
	
	public Date getLastHeartbeat() {
		return lastHeartbeat;
	}
	
	public void setLastHeartbeat(Date lastHeartbeat) {
		this.lastHeartbeat = lastHeartbeat;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "LiveLocationData [id=" + id + ", advocateId=" + advocateId + ", userId=" + userId + ", locationName="
				+ locationName + ", lattitude=" + lattitude + ", longitude=" + longitude + ", lastHeartbeat=" 
				+ lastHeartbeat + ", active=" + active + "]";
	}

}