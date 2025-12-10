package com.example.demo700.Model.AdvocateModels;

import java.util.Arrays;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.demo700.ENums.AdvocateSpeciality;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.lang.NonNull;

@Document(collection = "AdvocateJoinRequest")
public class AdvocateJoinRequest {

	@Id
	private String id;

	@NonNull
	private String userId;

	@NonNull
	private Set<AdvocateSpeciality> advocateSpeciality;

	private int experience;

	@NonNull
	private String licenseKey;

	@JsonIgnore
	private String cvHexKey;

	private String degrees[];

	private String workingExperiences[];

	public AdvocateJoinRequest(String userId, Set<AdvocateSpeciality> advocateSpeciality, int experience, String licenseKey,
			String cvHexKey, String[] degrees, String[] workingExperiences) {
		super();
		this.userId = userId;
		this.advocateSpeciality = advocateSpeciality;
		this.experience = experience;
		this.licenseKey = licenseKey;
		this.cvHexKey = cvHexKey;
		this.degrees = degrees;
		this.workingExperiences = workingExperiences;
	}

	public AdvocateJoinRequest() {
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
		return "Advocate [id=" + id + ", userId=" + userId + ", AdvocateSpeciality=" + advocateSpeciality.toString()
				+ ", experience=" + experience + ", licenseKey=" + licenseKey + ", cvHexKey=" + cvHexKey + ", degrees="
				+ Arrays.toString(degrees) + ", workingExperiences=" + Arrays.toString(workingExperiences) + "]";
	}

}
