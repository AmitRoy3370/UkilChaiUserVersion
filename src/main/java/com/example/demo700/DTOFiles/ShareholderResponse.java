package com.example.demo700.DTOFiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo700.Model.UserModels.CompanyInformation;

public class ShareholderResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1052L;

	private String id;

	private String userId, userName, profileImageId;

	private String contactInfoId, email, phone;

	private String locationId, locationName;

	private double lattitude, longititude;

	private String nid, tin;

	private List<CompanyInformation> companies = new ArrayList<>();
	
	private Map<String, List<Double>> sharePercentage = new HashMap<>();

	private Map<String, List<Double>> sharePercentageWithCompanyName = new HashMap<>();
	
	public ShareholderResponse(String id, String userId, String userName, String contactInfoId, String email,
			String phone, String locationId, String locationName, double lattitude, double longititude, String nid,
			String tin, Map<String, List<Double>> sharePercentage, String profileImageId) {
		super();
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.contactInfoId = contactInfoId;
		this.email = email;
		this.phone = phone;
		this.locationId = locationId;
		this.locationName = locationName;
		this.lattitude = lattitude;
		this.longititude = longititude;
		this.nid = nid;
		this.tin = tin;
		this.sharePercentage = sharePercentage;
		this.profileImageId = profileImageId;
	}

	public ShareholderResponse() {
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

	public String getContactInfoId() {
		return contactInfoId;
	}

	public void setContactInfoId(String contactInfoId) {
		this.contactInfoId = contactInfoId;
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

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

	public Map<String, List<Double>> getSharePercentage() {
		return sharePercentage;
	}

	public void setSharePercentage(Map<String, List<Double>> sharePercentage) {
		this.sharePercentage = sharePercentage;
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

	public Map<String, List<Double>> getSharePercentageWithCompanyName() {
		return sharePercentageWithCompanyName;
	}

	public void setSharePercentageWithCompanyName(Map<String, List<Double>> sharePercentageWithCompanyName) {
		this.sharePercentageWithCompanyName = sharePercentageWithCompanyName;
	}

	public String getProfileImageId() {
		return profileImageId;
	}

	public void setProfileImageId(String profileImageId) {
		this.profileImageId = profileImageId;
	}

	@Override
	public String toString() {
		return "ShareholderResponse [id=" + id + ", userId=" + userId + ", userName=" + userName + ", profileImageId="
				+ profileImageId + ", contactInfoId=" + contactInfoId + ", email=" + email + ", phone=" + phone
				+ ", locationId=" + locationId + ", locationName=" + locationName + ", lattitude=" + lattitude
				+ ", longititude=" + longititude + ", nid=" + nid + ", tin=" + tin + ", companies=" + companies
				+ ", sharePercentage=" + sharePercentage + ", sharePercentageWithCompanyName="
				+ sharePercentageWithCompanyName + "]";
	}

}
