package com.example.demo700.DTOFiles;

import java.util.List;

public class CenterAdminDTO {

	String id, profileImageId;

	String userId, userName;

	List<String> admins;

	List<String> districts;

	List<String> advocates;

	List<String> adminsName;

	List<String> advocatesName;

	public CenterAdminDTO(String id, String userId, List<String> admins, List<String> districts, List<String> advocates,
			List<String> adminsName, List<String> advocatesName, String userName, String profileImageId) {
		super();
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.admins = admins;
		this.districts = districts;
		this.advocates = advocates;
		this.adminsName = adminsName;
		this.advocatesName = advocatesName;
		this.profileImageId = profileImageId;
	}

	public CenterAdminDTO() {
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

	public List<String> getAdmins() {
		return admins;
	}

	public void setAdmins(List<String> admins) {
		this.admins = admins;
	}

	public List<String> getDistricts() {
		return districts;
	}

	public void setDistricts(List<String> districts) {
		this.districts = districts;
	}

	public List<String> getAdvocates() {
		return advocates;
	}

	public void setAdvocates(List<String> advocates) {
		this.advocates = advocates;
	}

	public List<String> getAdminsName() {
		return adminsName;
	}

	public void setAdminsName(List<String> adminsName) {
		this.adminsName = adminsName;
	}

	public List<String> getAdvocatesName() {
		return advocatesName;
	}

	public void setAdvocatesName(List<String> advocatesName) {
		this.advocatesName = advocatesName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProfileImageId() {
		return profileImageId;
	}

	public void setProfileImageId(String profileImageId) {
		this.profileImageId = profileImageId;
	}

	@Override
	public String toString() {
		return "CenterAdminDTO [id=" + id + ", profileImageId=" + profileImageId + ", userId=" + userId + ", userName="
				+ userName + ", admins=" + admins + ", districts=" + districts + ", advocates=" + advocates
				+ ", adminsName=" + adminsName + ", advocatesName=" + advocatesName + "]";
	}

}
