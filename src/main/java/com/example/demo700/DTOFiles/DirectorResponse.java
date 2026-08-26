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

	private String position;

	private String nid;
	
	private List<CompanyInformation> companies = new ArrayList<>();

	public DirectorResponse(String id, String userId, String userName, String userContactInfnfoId, String email,
			String phone, String locationId, String locationName, double lattitude, double longititude, String position,
			String nid, List<CompanyInformation> companies, String profileImageId) {
		super();
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.userContactInfnfoId = userContactInfnfoId;
		this.email = email;
		this.phone = phone;
		this.locationId = locationId;
		this.locationName = locationName;
		this.lattitude = lattitude;
		this.longititude = longititude;
		this.position = position;
		this.nid = nid;
		this.companies = companies;
		this.profileImageId = profileImageId;
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

	@Override
	public String toString() {
		return "DirectorResponse [id=" + id + ", userId=" + userId + ", userName=" + userName + ", profileImageId="
				+ profileImageId + ", userContactInfnfoId=" + userContactInfnfoId + ", email=" + email + ", phone="
				+ phone + ", locationId=" + locationId + ", locationName=" + locationName + ", lattitude=" + lattitude
				+ ", longititude=" + longititude + ", position=" + position + ", nid=" + nid + ", companies="
				+ companies + "]";
	}

}
