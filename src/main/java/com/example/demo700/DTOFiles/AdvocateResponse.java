package com.example.demo700.DTOFiles;

import java.util.Arrays;
import java.util.Set;

import com.example.demo700.ENums.AdvocateSpeciality;

public class AdvocateResponse {

	private String id, contactInfoId, locationId;

	private String userId, name, profileImageId;

	private Set<AdvocateSpeciality> advocateSpeciality;

	private int experience;

	private String licenseKey;

	private String cvHexKey;

	private String degrees[];

	private String workingExperiences[];

	private String email, phone;

	private String locationName;

	private double lattitude, longitude;

	public AdvocateResponse(String id, String contactInfoId, String locationId, String userId, String name,
			String profileImageId, Set<AdvocateSpeciality> advocateSpeciality, int experience, String licenseKey,
			String cvHexKey, String[] degrees, String[] workingExperiences, String email, String phone,
			String locationName, double lattitude, double longitude) {
		super();
		this.id = id;
		this.contactInfoId = contactInfoId;
		this.locationId = locationId;
		this.userId = userId;
		this.name = name;
		this.profileImageId = profileImageId;
		this.advocateSpeciality = advocateSpeciality;
		this.experience = experience;
		this.licenseKey = licenseKey;
		this.cvHexKey = cvHexKey;
		this.degrees = degrees;
		this.workingExperiences = workingExperiences;
		this.email = email;
		this.phone = phone;
		this.locationName = locationName;
		this.lattitude = lattitude;
		this.longitude = longitude;
	}

	public AdvocateResponse() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContactInfoId() {
		return contactInfoId;
	}

	public void setContactInfoId(String contactInfoId) {
		this.contactInfoId = contactInfoId;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfileImageId() {
		return profileImageId;
	}

	public void setProfileImageId(String profileImageId) {
		this.profileImageId = profileImageId;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
		return "AdvocateResponse [id=" + id + ", contactInfoId=" + contactInfoId + ", locationId=" + locationId
				+ ", userId=" + userId + ", name=" + name + ", profileImageId=" + profileImageId
				+ ", advocateSpeciality=" + advocateSpeciality + ", experience=" + experience + ", licenseKey="
				+ licenseKey + ", cvHexKey=" + cvHexKey + ", degrees=" + Arrays.toString(degrees)
				+ ", workingExperiences=" + Arrays.toString(workingExperiences) + ", email=" + email + ", phone="
				+ phone + ", locationName=" + locationName + ", lattitude=" + lattitude + ", longitude=" + longitude
				+ "]";
	}

}
