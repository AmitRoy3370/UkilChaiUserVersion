package com.example.demo700.DTOFiles;

import java.util.Set;

import com.example.demo700.ENums.AdvocateSpeciality;

public class AdminJoinRequestDTO {

	private String id, profileImageId;

	private String userId, userName;

	private Set<AdvocateSpeciality> advocateSpeciality;

	public AdminJoinRequestDTO(String userId, String userName, Set<AdvocateSpeciality> advocateSpeciality, String profileImageId) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.advocateSpeciality = advocateSpeciality;
		this.profileImageId = profileImageId;
	}

	public AdminJoinRequestDTO() {
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
		return "AdminJoinRequestDTO [id=" + id + ", profileImageId=" + profileImageId + ", userId=" + userId
				+ ", userName=" + userName + ", advocateSpeciality=" + advocateSpeciality + "]";
	}

}
