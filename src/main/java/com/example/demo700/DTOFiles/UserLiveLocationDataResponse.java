package com.example.demo700.DTOFiles;

public class UserLiveLocationDataResponse {

	private String id;

	private String advocateId;

	private String userId, userName;

	private String locationName;

	private double lattitude, longitude;

	public UserLiveLocationDataResponse(String id, String advocateId, String userId, String userName,
			String locationName, double lattitude, double longitude) {
		super();
		this.id = id;
		this.advocateId = advocateId;
		this.userId = userId;
		this.userName = userName;
		this.locationName = locationName;
		this.lattitude = lattitude;
		this.longitude = longitude;
	}

	public UserLiveLocationDataResponse() {
		super();
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
		return "UserLiveLocationDataResponse [id=" + id + ", advocateId=" + advocateId + ", userId=" + userId
				+ ", userName=" + userName + ", locationName=" + locationName + ", lattitude=" + lattitude
				+ ", longitude=" + longitude + "]";
	}

}
