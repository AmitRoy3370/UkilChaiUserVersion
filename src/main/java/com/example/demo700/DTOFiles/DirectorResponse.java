package com.example.demo700.DTOFiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import com.example.demo700.Model.UserModels.CompanyInformation;
import com.mongodb.lang.NonNull;

public class DirectorResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1051L;

	private String id;

	private String userId, userName, profileImageId;

	private String userContactInfnfoId, email, phone;

	private String locationId, locationName;

	private double lattitude, longititude;

	private String position, fullName, fatherName, motherName, nidNumber, mobileNumber, directorEmail;

	private String nid;

	private List<CompanyInformation> companies = new ArrayList<>();

	public DirectorResponse(String id, String userId, String userName, String profileImageId,
			String userContactInfnfoId, String email, String phone, String locationId, String locationName,
			double lattitude, double longititude, String position, String fullName, String fatherName,
			String motherName, String nidNumber, String mobileNumber, String directorEmail, String nid,
			List<CompanyInformation> companies) {
		super();
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.profileImageId = profileImageId;
		this.userContactInfnfoId = userContactInfnfoId;
		this.email = email;
		this.phone = phone;
		this.locationId = locationId;
		this.locationName = locationName;
		this.lattitude = lattitude;
		this.longititude = longititude;
		this.position = position;
		this.fullName = fullName;
		this.fatherName = fatherName;
		this.motherName = motherName;
		this.nidNumber = nidNumber;
		this.mobileNumber = mobileNumber;
		this.directorEmail = directorEmail;
		this.nid = nid;
		this.companies = companies;
	}

	public DirectorResponse() {
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserContactInfnfoId() {
		return userContactInfnfoId;
	}

	public void setUserContactInfnfoId(String userContactInfnfoId) {
		this.userContactInfnfoId = userContactInfnfoId;
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

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
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

	public double getLongititude() {
		return longititude;
	}

	public void setLongititude(double longititude) {
		this.longititude = longititude;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<CompanyInformation> getCompanies() {
		return companies;
	}

	public void setCompanies(List<CompanyInformation> companies) {
		this.companies = companies;
	}

	public String getProfileImageId() {
		return profileImageId;
	}

	public void setProfileImageId(String profileImageId) {
		this.profileImageId = profileImageId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getNidNumber() {
		return nidNumber;
	}

	public void setNidNumber(String nidNumber) {
		this.nidNumber = nidNumber;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getDirectorEmail() {
		return directorEmail;
	}

	public void setDirectorEmail(String directorEmail) {
		this.directorEmail = directorEmail;
	}

	@Override
	public String toString() {
		return "DirectorResponse [id=" + id + ", userId=" + userId + ", userName=" + userName + ", profileImageId="
				+ profileImageId + ", userContactInfnfoId=" + userContactInfnfoId + ", email=" + email + ", phone="
				+ phone + ", locationId=" + locationId + ", locationName=" + locationName + ", lattitude=" + lattitude
				+ ", longititude=" + longititude + ", position=" + position + ", fullName=" + fullName + ", fatherName="
				+ fatherName + ", motherName=" + motherName + ", nidNumber=" + nidNumber + ", mobileNumber="
				+ mobileNumber + ", directorEmail=" + directorEmail + ", nid=" + nid + ", companies=" + companies + "]";
	}

}
