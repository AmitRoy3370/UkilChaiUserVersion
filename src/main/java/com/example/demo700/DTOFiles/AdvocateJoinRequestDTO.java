package com.example.demo700.DTOFiles;

import java.util.Arrays;
import java.util.Set;

import com.example.demo700.ENums.AdvocateSpeciality;

public class AdvocateJoinRequestDTO {

	private String id, profileImageId;

	private String userId, userName;

	private Set<AdvocateSpeciality> advocateSpeciality;

	private int experience;

	private String licenseKey;

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

	@Override
	public String toString() {
		return "AdvocateJoinRequestDTO [id=" + id + ", profileImageId=" + profileImageId + ", userId=" + userId
				+ ", userName=" + userName + ", advocateSpeciality=" + advocateSpeciality + ", experience=" + experience
				+ ", licenseKey=" + licenseKey + ", cvHexKey=" + cvHexKey + ", degrees=" + Arrays.toString(degrees)
				+ ", workingExperiences=" + Arrays.toString(workingExperiences) + "]";
	}

}
