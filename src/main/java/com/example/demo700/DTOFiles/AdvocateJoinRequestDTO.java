package com.example.demo700.DTOFiles;

import java.util.Arrays;
import java.util.Set;

import com.example.demo700.ENums.AdvocateSpeciality;

public class AdvocateJoinRequestDTO {

	private String id, profileImageId;

	private String userId, userName, fullName;
	
	private String userContactInfoId, phone, email;
	
	private String userLocationId, locationName;
	
	private double lattitude, logitude;

	private Set<AdvocateSpeciality> advocateSpeciality;

	private int experience;

	private String licenseKey, district;

	private String cvHexKey;

	private String degrees[];

	private String workingExperiences[];

	public AdvocateJoinRequestDTO(String id, String profileImageId, String userId, String userName,
			Set<AdvocateSpeciality> advocateSpeciality, int experience, String licenseKey, String cvHexKey,
			String[] degrees, String[] workingExperiences) {
		super();
		this.id = id;
		this.profileImageId = profileImageId;
		this.userId = userId;
		this.userName = userName;
		this.advocateSpeciality = advocateSpeciality;
		this.experience = experience;
		this.licenseKey = licenseKey;
		this.cvHexKey = cvHexKey;
		this.degrees = degrees;
		this.workingExperiences = workingExperiences;
	}

	public AdvocateJoinRequestDTO(String id, String profileImageId, String userId, String userName, String fullName,
			String userContactInfoId, String phone, String email, String userLocationId, String locationName,
			double lattitude, double logitude, Set<AdvocateSpeciality> advocateSpeciality, int experience,
			String licenseKey, String district, String cvHexKey, String[] degrees, String[] workingExperiences) {
		super();
		this.id = id;
		this.profileImageId = profileImageId;
		this.userId = userId;
		this.userName = userName;
		this.fullName = fullName;
		this.userContactInfoId = userContactInfoId;
		this.phone = phone;
		this.email = email;
		this.userLocationId = userLocationId;
		this.locationName = locationName;
		this.lattitude = lattitude;
		this.logitude = logitude;
		this.advocateSpeciality = advocateSpeciality;
		this.experience = experience;
		this.licenseKey = licenseKey;
		this.district = district;
		this.cvHexKey = cvHexKey;
		this.degrees = degrees;
		this.workingExperiences = workingExperiences;
	}

	public AdvocateJoinRequestDTO(String id, String profileImageId, String userId, String userName,
			String userContactInfoId, String phone, String email, String userLocationId, String locationName,
			double lattitude, double logitude, Set<AdvocateSpeciality> advocateSpeciality, int experience,
			String licenseKey, String district, String cvHexKey, String[] degrees, String[] workingExperiences) {
		super();
		this.id = id;
		this.profileImageId = profileImageId;
		this.userId = userId;
		this.userName = userName;
		this.userContactInfoId = userContactInfoId;
		this.phone = phone;
		this.email = email;
		this.userLocationId = userLocationId;
		this.locationName = locationName;
		this.lattitude = lattitude;
		this.logitude = logitude;
		this.advocateSpeciality = advocateSpeciality;
		this.experience = experience;
		this.licenseKey = licenseKey;
		this.district = district;
		this.cvHexKey = cvHexKey;
		this.degrees = degrees;
		this.workingExperiences = workingExperiences;
	}

	public AdvocateJoinRequestDTO() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProfileImageId() {
		return profileImageId;
	}

	public void setProfileImageId(String profileImageId) {
		this.profileImageId = profileImageId;
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

	public Set<AdvocateSpeciality> getAdvocateSpeciality() {
		return advocateSpeciality;
	}

	public void setAdvocateSpeciality(Set<AdvocateSpeciality> advocateSpeciality) {
		this.advocateSpeciality = advocateSpeciality;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public String getLicenseKey() {
		return licenseKey;
	}

	public void setLicenseKey(String licenseKey) {
		this.licenseKey = licenseKey;
	}

	public String getCvHexKey() {
		return cvHexKey;
	}

	public void setCvHexKey(String cvHexKey) {
		this.cvHexKey = cvHexKey;
	}

	public String[] getDegrees() {
		return degrees;
	}

	public void setDegrees(String[] degrees) {
		this.degrees = degrees;
	}

	public String[] getWorkingExperiences() {
		return workingExperiences;
	}

	public void setWorkingExperiences(String[] workingExperiences) {
		this.workingExperiences = workingExperiences;
	}

	public String getUserContactInfoId() {
		return userContactInfoId;
	}

	public void setUserContactInfoId(String userContactInfoId) {
		this.userContactInfoId = userContactInfoId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserLocationId() {
		return userLocationId;
	}

	public void setUserLocationId(String userLocationId) {
		this.userLocationId = userLocationId;
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

	public double getLogitude() {
		return logitude;
	}

	public void setLogitude(double logitude) {
		this.logitude = logitude;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public String toString() {
		return "AdvocateJoinRequestDTO [id=" + id + ", profileImageId=" + profileImageId + ", userId=" + userId
				+ ", userName=" + userName + ", fullName=" + fullName + ", userContactInfoId=" + userContactInfoId
				+ ", phone=" + phone + ", email=" + email + ", userLocationId=" + userLocationId + ", locationName="
				+ locationName + ", lattitude=" + lattitude + ", logitude=" + logitude + ", advocateSpeciality="
				+ advocateSpeciality + ", experience=" + experience + ", licenseKey=" + licenseKey + ", district="
				+ district + ", cvHexKey=" + cvHexKey + ", degrees=" + Arrays.toString(degrees)
				+ ", workingExperiences=" + Arrays.toString(workingExperiences) + "]";
	}

}
